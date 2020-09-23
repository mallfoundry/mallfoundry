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

package org.mallfoundry.edw.stream.sales;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.edw.sales.SalesFact;
import org.mallfoundry.edw.sales.SalesFactManager;
import org.mallfoundry.edw.time.DateDimensionManager;
import org.mallfoundry.edw.time.TimeDimensionManager;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderItem;
import org.mallfoundry.order.OrdersPaidEvent;
import org.mallfoundry.payment.PaymentStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SalesEventStreams {

    private final DateDimensionManager dateDimensionManager;

    private final TimeDimensionManager timeDimensionManager;

    private final SalesFactManager salesFactManager;

    public SalesEventStreams(DateDimensionManager dateDimensionManager,
                             TimeDimensionManager timeDimensionManager,
                             SalesFactManager salesFactManager) {
        this.dateDimensionManager = dateDimensionManager;
        this.timeDimensionManager = timeDimensionManager;
        this.salesFactManager = salesFactManager;
    }

    private SalesFact createSalesFact(Order order, OrderItem item) {
        var factKey = this.salesFactManager.createSalesFactKey();
        factKey.setTenantKey(order.getTenantId());
        factKey.setStoreKey(order.getStoreId());
        factKey.setCustomerKey(order.getCustomerId());
        factKey.setStoreKey(order.getStoreId());
        factKey.setProductKey(item.getProductId());
        factKey.setVariantKey(item.getVariantId());
        factKey.setDateKey(this.dateDimensionManager.createDateDimensionKey(order.getPaidTime()));
        factKey.setTimeKey(this.timeDimensionManager.createTimeDimensionKey(order.getPlacedTime()));
        var fact = this.salesFactManager.createSalesFact(factKey);
        fact.setSalesQuantity(item.getQuantity());
        fact.setSalesAmount(item.getTotalAmount());
        return fact;
    }

    private List<SalesFact> mapToOrderFacts(Order order) {
        return order.getItems().stream()
                .map(item -> this.createSalesFact(order, item))
                .collect(Collectors.toUnmodifiableList());
    }

    @EventListener
    public void onOrdersPaidEvent(OrdersPaidEvent event) {
        var facts = event.getOrders()
                .stream()
                .filter(order -> PaymentStatus.CAPTURED.equals(order.getPaymentStatus()))
                .map(this::mapToOrderFacts)
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableList());
        if (CollectionUtils.isNotEmpty(facts)) {
            this.salesFactManager.saveSalesFacts(facts);
        }
    }
}
