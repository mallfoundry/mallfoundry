package com.mallfoundry.order;

import java.util.List;
import java.util.Optional;

public interface Order {

    String getId();

    void setId(String id);

    List<OrderItem> getItems(List<String> itemIds);

    List<Shipment> getShipments();

    Optional<Shipment> getShipment(String id);

    void addShipment(Shipment shipment);
}
