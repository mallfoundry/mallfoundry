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
import org.mallfoundry.edw.order.OrderFactManager;
import org.mallfoundry.edw.time.DateDimensionManager;
import org.mallfoundry.edw.time.TimeDimensionManager;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrdersPaidEvent;
import org.mallfoundry.payment.PaymentStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.stream.Collectors;

@Configuration
public class EdwOrderFactEventStreams {

    private final DateDimensionManager dateDimensionManager;

    private final TimeDimensionManager timeDimensionManager;

    private final OrderFactManager orderFactManager;

    public EdwOrderFactEventStreams(DateDimensionManager dateDimensionManager,
                                    TimeDimensionManager timeDimensionManager,
                                    OrderFactManager orderFactManager) {
        this.dateDimensionManager = dateDimensionManager;
        this.timeDimensionManager = timeDimensionManager;
        this.orderFactManager = orderFactManager;
    }

    private OrderFact mapToOrderFacts(Order order) {
        var factKey = this.orderFactManager.createOrderFactKey();
        factKey.setKey(order.getId());
        factKey.setTenantKey(order.getTenantId());
        factKey.setStoreKey(order.getStoreId());
        factKey.setCustomerKey(order.getCustomerId());
        factKey.setPlacedDateKey(this.dateDimensionManager.createDateDimensionKey(order.getPlacedTime()));
        factKey.setPlacedTimeKey(this.timeDimensionManager.createTimeDimensionKey(order.getPlacedTime()));
        factKey.setPaidDateKey(this.dateDimensionManager.createDateDimensionKey(order.getPaidTime()));
        factKey.setPaidTimeKey(this.timeDimensionManager.createTimeDimensionKey(order.getPaidTime()));
        var fact = this.orderFactManager.createOrderFact(factKey);
        fact.setTotalPrice(order.getTotalPrice());
        fact.setTotalQuantity(order.getTotalQuantity());
        fact.setTotalAmount(order.getTotalAmount());
        return fact;
    }

    @EventListener
    public void onOrdersPaidEvent(OrdersPaidEvent event) {
        var facts = event.getOrders()
                .stream()
                .filter(order -> PaymentStatus.CAPTURED.equals(order.getPaymentStatus()))
                .map(this::mapToOrderFacts)
                .collect(Collectors.toUnmodifiableList());
        if (CollectionUtils.isNotEmpty(facts)) {
            this.orderFactManager.saveOrderFacts(facts);
        }
    }
}
