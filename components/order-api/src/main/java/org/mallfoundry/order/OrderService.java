package org.mallfoundry.order;

import org.mallfoundry.data.SliceList;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderQuery createOrderQuery();

    Order createOrder(String id);

    List<Order> placeOrder(Order order);

    List<Order> placeOrders(List<Order> orders);

    Order updateOrder(Order order);

    Optional<Order> getOrder(String orderId);

    SliceList<Order> getOrders(OrderQuery query);

    void payOrder(String orderId, PaymentDetails details);

    void cancelOrder(String orderId, String reason);

    Shipment createShipment(String orderId, List<String> itemIds);

    Shipment addShipment(String orderId, Shipment shipment);

    Optional<Shipment> getShipment(String orderId, String shipmentId);

    void setShipment(String orderId, Shipment shipment);

    void setShipments(String orderId, List<Shipment> shipments);

    void removeShipment(String orderId, String shipmentId);

}
