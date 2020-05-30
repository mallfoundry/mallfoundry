package org.mallfoundry.checkout;

import org.mallfoundry.catalog.ProductService;
import org.mallfoundry.inventory.InventoryService;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderService;
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

    private final ProductService productService;

    private final InventoryService inventoryService;

    private final OrderService orderService;

    public InternalCheckoutService(ProductService productService,
                                   InventoryService inventoryService,
                                   OrderService orderService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.orderService = orderService;
    }

    @Override
    public Checkout createCheckout() {
        return new InternalCheckout();
    }

    private List<InternalCheckoutItem> ofCheckoutItems(List<CheckoutItem> items) {
        return items.stream().map(InternalCheckoutItem::of).collect(Collectors.toList());
    }

    private Optional<Order> findOrderByStoreId(List<Order> orders, String storeId) {
        return orders.stream().filter(order -> Objects.equals(order.getStoreId(), storeId)).findFirst();
    }

    private Checkout checkout(InternalCheckout checkout) {
        var items = ofCheckoutItems(checkout.getItems());

        var orders = new ArrayList<Order>();

        for (var item : items) {
            if (item.getQuantity() <= 0) {
                throw new CheckoutException("The checkout item must be greater than zero");
            }

            this.inventoryService.adjustInventory(
                    this.inventoryService.createInventoryAdjustment()
                            .toBuilder()
                            .productId(item.getProductId())
                            .variantId(item.getVariantId())
                            .quantityDelta(-item.getQuantity())
                            .build());

            var product = this.productService.getProduct(item.getProductId()).orElseThrow();
            var variant = product.getVariant(item.getVariantId()).orElseThrow();
            var order = this.findOrderByStoreId(orders, product.getStoreId())
                    .orElseGet(() -> this.orderService
                            .createEmptyOrder()
                            .toBuilder()
                            .shippingAddress(checkout.getShippingAddress())
                            .build());

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

        checkout.setOrders(this.orderService.placeOrders(orders));
        return checkout;
    }

    @Transactional
    @Override
    public Checkout checkout(Checkout checkout) {
        return checkout(InternalCheckout.of(checkout));
    }
}
