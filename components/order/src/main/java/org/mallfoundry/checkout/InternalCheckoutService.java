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

import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.customer.CustomerService;
import org.mallfoundry.inventory.InventoryService;
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
public class InternalCheckoutService implements CheckoutService {

    static final String CHECKOUT_ID_VALUE_NAME = "checkout.id";

    private final CustomerService customerService;

    private final StoreService storeService;

    private final ProductService productService;

    private final InventoryService inventoryService;

    private final OrderService orderService;

    private final CheckoutRepository checkoutRepository;

    public InternalCheckoutService(CustomerService customerService,
                                   StoreService storeService,
                                   ProductService productService,
                                   InventoryService inventoryService,
                                   OrderService orderService,
                                   CheckoutRepository checkoutRepository) {
        this.customerService = customerService;
        this.storeService = storeService;
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.orderService = orderService;
        this.checkoutRepository = checkoutRepository;
    }

    @Override
    public Checkout createCheckout(String id) {
        return new InternalCheckout(id);
    }

//    private List<InternalCheckoutItem> ofCheckoutItems(List<CheckoutItem> items) {
//        return items.stream().map(InternalCheckoutItem::of).collect(Collectors.toList());
//    }

    private Optional<Order> findOrderByStoreId(List<Order> orders, String storeId) {
        return orders.stream().filter(order -> Objects.equals(order.getStoreId(), storeId)).findFirst();
    }

    private List<Order> createOrders(InternalCheckout checkout) {
        var items = checkout.getItems();
        var orders = new ArrayList<Order>();
        for (var item : items) {
            var product = this.productService.getProduct(item.getProductId()).orElseThrow();
            var variant = product.getVariant(item.getVariantId()).orElseThrow();
            var order = this.findOrderByStoreId(orders, product.getStoreId())
                    .orElseGet(() -> this.orderService.createOrder((String) null).toBuilder()
                            .shippingAddress(checkout.getShippingAddress()).build());
            var store = this.storeService.getStore(product.getStoreId()).orElseThrow();
            order.setStoreId(store.getId());
            order.setStoreName(store.getName());
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

    private Address getCustomerDefaultAddress() {
        var customerAddress = this.customerService.getDefaultAddress(SubjectHolder.getUserId()).orElseThrow();
        var address = new DefaultAddress();
        BeanUtils.copyProperties(customerAddress, address);
        return address;
    }

    private Checkout createCheckout(InternalCheckout checkout) {
        if (Objects.isNull(checkout.getId())) {
            checkout.setId(PrimaryKeyHolder.next(CHECKOUT_ID_VALUE_NAME));
        }
        if (Objects.isNull(checkout.getShippingAddress())) {
            checkout.setShippingAddress(this.getCustomerDefaultAddress());
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

    @Override
    public Optional<Checkout> getCheckout(String id) {
        var checkout = this.checkoutRepository.findById(id).orElseThrow();
        this.setOrdersToCheckout(checkout);
        return Optional.of(checkout);
    }

    @Transactional
    @Override
    public Checkout updateCheckout(Checkout checkout) {
        var storedCheckout = this.checkoutRepository.findById(checkout.getId()).orElseThrow();

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
        var checkout = this.checkoutRepository.findById(id).orElseThrow();
        this.checkoutRepository.delete(checkout);
    }

    private void adjustInventories(List<CheckoutItem> items) {
        var adjustments = items.stream()
                .peek(item -> {
                    if (item.getQuantity() <= 0) {
                        throw new CheckoutException("The checkout item must be greater than zero");
                    }
                })
                .map(item -> this.inventoryService.createInventoryAdjustment().toBuilder()
                        .productId(item.getProductId()).variantId(item.getVariantId())
                        .quantityDelta(-item.getQuantity()).build())
                .collect(Collectors.toUnmodifiableList());
        this.inventoryService.adjustInventories(adjustments);
    }

    private void placeOrders(InternalCheckout checkout) {
        checkout.setOrders(this.orderService.placeOrders(checkout.getOrders()));
    }

    @Override
    @Transactional
    public Checkout placeCheckout(String id) {
        var checkout = this.checkoutRepository.findById(id).orElseThrow();
        this.adjustInventories(checkout.getItems());
        this.setOrdersToCheckout(checkout);
        this.placeOrders(checkout);
        return checkout;
    }
}
