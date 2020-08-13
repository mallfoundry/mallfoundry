/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.checkout;

import org.mallfoundry.cart.CartService;
import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.customer.CustomerService;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderService;
import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.shipping.Address;
import org.mallfoundry.shipping.DefaultAddress;
import org.mallfoundry.store.StoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultCheckoutService implements CheckoutService {

    static final String CHECKOUT_ID_VALUE_NAME = "checkout.id";

    private final CustomerService customerService;

    private final StoreService storeService;

    private final CartService cartService;

    private final ProductService productService;

    private final OrderService orderService;

    private final CheckoutRepository checkoutRepository;

    public DefaultCheckoutService(CustomerService customerService,
                                  StoreService storeService,
                                  CartService cartService,
                                  ProductService productService,
                                  OrderService orderService,
                                  CheckoutRepository checkoutRepository) {
        this.customerService = customerService;
        this.storeService = storeService;
        this.cartService = cartService;
        this.productService = productService;
        this.orderService = orderService;
        this.checkoutRepository = checkoutRepository;
    }

    @Override
    public Checkout createCheckout(String id) {
        return new InternalCheckout(id);
    }

    private Optional<Order> findOrderByStoreId(List<Order> orders, String storeId) {
        return orders.stream().filter(order -> Objects.equals(order.getStoreId(), storeId)).findFirst();
    }

    private List<Order> createOrders(InternalCheckout checkout) {
        var items = checkout.getItems();
        var orders = new ArrayList<Order>();
        for (var item : items) {
            var product = this.productService.getProduct(item.getProductId());
            var variant = product.getVariant(item.getVariantId());
            var order = this.findOrderByStoreId(orders, product.getStoreId())
                    .orElseGet(() -> this.orderService.createOrder((String) null).toBuilder()
                            .shippingAddress(checkout.getShippingAddress()).build());
            var store = this.storeService.getStore(product.getStoreId());
            order.setStoreId(store.getId());
            order.setStoreName(store.getName());
            order.setSource(checkout.getSource());
            order.addItem(order.createItem(null)
                    .toBuilder()
                    .storeId(product.getStoreId())
                    .productId(product.getId())
                    .variantId(variant.getId())
                    .name(product.getName())
                    .optionSelections(List.copyOf(variant.getOptionSelections()))
                    .imageUrl(CollectionUtils.firstElement(variant.getImageUrls()))
                    .price(variant.getPrice())
                    .quantity(item.getQuantity())
                    .build());

            // Add to orders.
            if (!orders.contains(order)) {
                orders.add(order);
            }
        }
        return orders;
    }

    private void setOrdersToCheckout(InternalCheckout checkout) {
        checkout.setOrders(this.createOrders(checkout));
    }

    private Address getDefaultCustomerAddress() {
        var customerAddress = this.customerService.getDefaultCustomerAddress(SubjectHolder.getSubject().getId()).orElseThrow();
        var address = new DefaultAddress();
        BeanUtils.copyProperties(customerAddress, address);
        return address;
    }

    private Checkout createCheckout(InternalCheckout checkout) {
        if (Objects.isNull(checkout.getId())) {
            checkout.setId(PrimaryKeyHolder.next(CHECKOUT_ID_VALUE_NAME));
        }
        if (Objects.isNull(checkout.getShippingAddress())) {
            checkout.setShippingAddress(this.getDefaultCustomerAddress());
        }
        this.setOrdersToCheckout(checkout);
        checkout.create();
        return this.checkoutRepository.save(checkout);
    }

    @Transactional
    @Override
    public Checkout createCheckout(Checkout checkout) {
        return createCheckout(InternalCheckout.of(checkout));
    }

    private InternalCheckout requiredCheckout(String id) {
        return this.checkoutRepository.findById(id).orElseThrow(CheckoutExceptions::notFound);
    }


    @Override
    public Optional<Checkout> getCheckout(String id) {
        var checkout = this.requiredCheckout(id);
        this.setOrdersToCheckout(checkout);
        return Optional.of(checkout);
    }

    @Transactional
    @Override
    public Checkout updateCheckout(Checkout checkout) {
        var storedCheckout = this.requiredCheckout(checkout.getId());
        if (Objects.nonNull(checkout.getShippingAddress())) {
            storedCheckout.setShippingAddress(checkout.getShippingAddress());
        }
        var newCheckout = this.checkoutRepository.save(storedCheckout);
        this.setOrdersToCheckout(newCheckout);
        return newCheckout;
    }

    @Transactional
    @Override
    public void deleteCheckout(String id) {
        var checkout = this.requiredCheckout(id);
        this.checkoutRepository.delete(checkout);
    }

    private void tryAdjustCart(Checkout checkout) {
        var factory = this.cartService.createCart((String) null);
        if (Objects.isNull(checkout.getCartId())) {
            return;
        }
        var adjustments = checkout.getItems().stream().filter(item -> item.getQuantity() != 0)
                .map(item -> factory.createAdjustment(null).toBuilder()
                        .productId(item.getProductId()).variantId(item.getVariantId())
                        .quantityDelta(-item.getQuantity()).build())
                .collect(Collectors.toUnmodifiableList());
        this.cartService.adjustCart(checkout.getCartId(), adjustments);
    }

    private void placeOrders(InternalCheckout checkout) {
        checkout.setOrders(this.orderService.placeOrders(checkout.getOrders()));
    }

    @Override
    @Transactional
    public Checkout placeCheckout(String id) {
        var checkout = this.requiredCheckout(id);
        this.setOrdersToCheckout(checkout);
        this.placeOrders(checkout);
        this.tryAdjustCart(checkout);
        return checkout;
    }
}
