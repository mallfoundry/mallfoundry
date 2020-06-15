package org.mallfoundry.order;

import org.mallfoundry.data.SliceList;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderQuery createOrderQuery();

    Order createOrder(String id);

    List<Order> placeOrder(Order order);

    List<Order> placeOrders(List<Order> orders);

    List<Order> splitOrders(List<Order> orders);

    Order updateOrder(Order order);

    Optional<Order> getOrder(String orderId);

    SliceList<Order> getOrders(OrderQuery query);

    void payOrder(String orderId, PaymentDetails details);

    void cancelOrder(String orderId, String reason);

    void packOrder(String orderId);

    void pickupOrder(String orderId);

    Shipment addOrderShipment(String orderId, Shipment shipment);

    Optional<Shipment> getOrderShipment(String orderId, String shipmentId);

    List<Shipment> getOrderShipments(String orderId);

    void setOrderShipment(String orderId, Shipment shipment);

    void setOrderShipments(String orderId, List<Shipment> shipments);

    void removeOrderShipment(String orderId, String shipmentId);

}
