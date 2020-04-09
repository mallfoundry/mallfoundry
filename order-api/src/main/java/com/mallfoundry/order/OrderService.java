package com.mallfoundry.order;

import java.util.List;

public interface OrderService {

    BillingAddress createBillingAddress(
            String countryCode, String postalCode, String consignee, String mobile, String address, String location);

    ShippingAddress createShippingAddress(
            String countryCode, String postalCode, String consignee, String mobile, String address, String location);

    Order createOrder(BillingAddress billingAddress, ShippingAddress shippingAddress, List<OrderItem> items);

    OrderItem createOrderItem(String productId, String variantId, int quantity);

    Shipment createShipment(String orderId, List<String> itemIds);

    void checkout(Order order);

    Shipment addShipment(Shipment shipment);
}
