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

package org.mallfoundry.edw.stream.order;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.edw.order.OrderFact;
import org.mallfoundry.edw.order.OrderFactRepository;
import org.mallfoundry.edw.time.TimeDimensions;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrdersPaidEvent;
import org.mallfoundry.payment.PaymentStatus;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

public class OrderEventStreams {

    private final OrderFactRepository orderFactRepository;

    public OrderEventStreams(OrderFactRepository orderFactRepository) {
        this.orderFactRepository = orderFactRepository;
    }

    private OrderFact mapToOrderFact(Order order) {
        var fact = this.orderFactRepository.create();
        fact.setTenantId(order.getTenantId());
        fact.setStoreId(order.getStoreId());
        fact.setCustomerId(order.getCustomerId());
        fact.setId(order.getId());
        fact.setPlacedTimeId(TimeDimensions.idOf(order.getPlacedTime()));
        fact.setPaidTimeId(TimeDimensions.idOf(order.getPaidTime()));
        fact.setFulfilledTimeId(TimeDimensions.idOf(order.getFulfilledTime()));
        // mms
        fact.setTotalQuantity(order.getTotalQuantity());
        fact.setTotalPrice(order.getTotalPrice());
        fact.setTotalShippingCost(order.getTotalShippingCost());
        fact.setTotalDiscountShippingCost(order.getTotalDiscountShippingCost());
        fact.setTotalDiscountTotalPrice(order.getTotalDiscountTotalPrice());
        fact.setSubtotalAmount(order.getSubtotalAmount());
        fact.setTotalAmount(order.getTotalAmount());
        return fact;
    }

    @Transactional
    @EventListener
    public void onOrdersPaidEvent(OrdersPaidEvent event) {
        var facts = event.getOrders()
                .stream()
                .filter(order -> PaymentStatus.CAPTURED.equals(order.getPaymentStatus()))
                .map(this::mapToOrderFact)
                .collect(Collectors.toUnmodifiableList());
        if (CollectionUtils.isNotEmpty(facts)) {
            this.orderFactRepository.saveAll(facts);
        }
    }
}
