package org.mallfoundry.analytics.stream.order;

import org.mallfoundry.analytics.stream.models.DateDimension;
import org.mallfoundry.analytics.stream.models.OrderItemFact;
import org.mallfoundry.analytics.stream.models.OrderStatusDimension;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderItem;
import org.mallfoundry.order.OrdersPlacedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class OrderEventStreams {

    private final OrderItemFactRepository orderItemFactRepository;

    private final OrderQuantityFactRepository orderQuantityFactRepository;

    public OrderEventStreams(OrderItemFactRepository orderItemFactRepository,
                             OrderQuantityFactRepository orderQuantityFactRepository) {
        this.orderItemFactRepository = orderItemFactRepository;
        this.orderQuantityFactRepository = orderQuantityFactRepository;
    }

    private OrderItemFact createFact(Order order, OrderItem item) {
        var fact = new OrderItemFact();
        // Set from order
        fact.setOrderId(order.getId());
        fact.setCustomerId(order.getCustomerId());
        fact.setCreatedDateId(DateDimension.idOf(order.getCreatedTime()));
        fact.setStatusId(OrderStatusDimension.idOf(order.getStatus()));
        // Set from order item
        fact.setId(item.getId());
        fact.setStoreId(item.getStoreId());
        fact.setProductId(item.getProductId());
        fact.setVariantId(item.getVariantId());
        fact.setPrice(item.getPrice());
        fact.setQuantity(item.getQuantity());
        fact.setShippingCost(item.getShippingCost());
        fact.setSubtotalAmount(item.getSubtotalAmount());
        fact.setTotalAmount(item.getTotalAmount());
        return fact;
    }

    private List<OrderItemFact> createFacts(Order order) {
        return order.getItems().stream()
                .map(item -> this.createFact(order, item))
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    @EventListener(OrdersPlacedEvent.class)
    public void handleOrderPlacedEvent(OrdersPlacedEvent event) {
        var items = event.getOrders()
                .stream()
                .map(this::createFacts)
                .flatMap(List::stream)
                .collect(Collectors.toUnmodifiableList());
        this.countOrderItemFacts(
                this.orderItemFactRepository.saveAll(items));
    }

    private void countOrderItemFacts(List<OrderItemFact> items) {
        this.orderQuantityFactRepository.saveAll(
                this.orderItemFactRepository.countAll(items));
    }
}
