package com.mallfoundry.order;

import com.mallfoundry.data.SliceList;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {

    ShippingAddress createShippingAddress();

    OrderQuery createOrderQuery();

    Order createOrder(ShippingAddress shippingAddress, List<OrderItem> items);

    OrderItem createOrderItem(String productId, String variantId, int quantity);

    Shipment createShipment(String orderId, List<String> itemIds);

    List<Order> checkout(Order order);

    List<Order> checkout(List<Order> orders);

    Shipment addShipment(Shipment shipment);

    Optional<Order> getOrder(String orderId);

    SliceList<Order> getOrders(OrderQuery query);

    void changeDiscountAmount(String orderId, Map<String, BigDecimal> amounts);

//    void changeDiscountAmount(String orderId, BigDecimal amount);

//    void discount(String orderId, String itemId, BigDecimal decimal);

//    void refund(Refund refund);

    void cancelOrder(String orderId);
}
