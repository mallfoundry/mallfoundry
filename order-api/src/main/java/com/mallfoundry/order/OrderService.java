package com.mallfoundry.order;

import java.util.List;

public interface OrderService {

    Shipment createShipment(String orderId, List<String> itemIds);

    Shipment addShipment(Shipment shipment);
}
