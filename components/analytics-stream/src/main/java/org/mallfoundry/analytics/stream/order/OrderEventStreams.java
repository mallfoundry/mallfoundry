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

package org.mallfoundry.analytics.stream.order;

import org.mallfoundry.dw.DateDimension;
import org.mallfoundry.dw.OrderItemFact;
import org.mallfoundry.dw.OrderStatusDimension;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderItem;
import org.mallfoundry.order.OrderPaidEvent;
import org.mallfoundry.order.OrderPlacedEvent;
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

    private void countOrderItemFacts(List<OrderItemFact> items) {
        this.orderQuantityFactRepository.deleteAll(items);
        this.orderQuantityFactRepository.saveAll(
                this.orderItemFactRepository.countAll(items));
    }

    @Transactional
    @EventListener
    public void handleOrderPlacedEvent(OrderPlacedEvent event) {
        var items = this.createFacts(event.getOrder());
        this.countOrderItemFacts(this.orderItemFactRepository.saveAll(items));
    }

    @Transactional
    @EventListener
    public void handleOrderPaidEvent(OrderPaidEvent event) {
        var items = this.createFacts(event.getOrder());
        this.countOrderItemFacts(this.orderItemFactRepository.saveAll(items));
    }
}
