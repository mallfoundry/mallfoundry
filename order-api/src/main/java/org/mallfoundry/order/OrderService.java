package org.mallfoundry.order;

import org.mallfoundry.data.SliceList;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderQuery createOrderQuery();

    Order createOrder(String id);

    List<Order> placeOrder(Order order);

    List<Order> placeOrders(List<Order> orders);

    void addShipment(String orderId, Shipment shipment);

    Optional<Shipment> getShipment(String orderId, String shipmentId);

    void setShipment(String orderId, Shipment shipment);

    void removeShipment(String orderId, String shipmentId);

    Optional<Order> getOrder(String orderId);

    SliceList<Order> getOrders(OrderQuery query);

    void payOrder(String orderId, PaymentDetails details);

    void cancelOrder(String orderId, String reason);
}
