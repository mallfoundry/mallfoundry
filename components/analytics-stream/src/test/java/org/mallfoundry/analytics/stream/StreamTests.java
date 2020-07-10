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

package org.mallfoundry.analytics.stream;

import org.junit.jupiter.api.Test;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderPlacedEvent;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@StandaloneTest
public class StreamTests {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Test
    public void testStream() {

        var testOrder = new TestOrder();
        testOrder.setId("1");
        testOrder.setCustomerId("1");
        testOrder.setCreatedTime(new Date());

        var item1 = new TestOrderItem();
        item1.setId("1");
        item1.setPrice(BigDecimal.valueOf(10.11));
        item1.setQuantity(11);
        item1.setShippingCost(BigDecimal.valueOf(10));
        testOrder.setItems(List.of(item1));
        this.eventPublisher.publishEvent(new OrderPlacedEvent() {
            @Override
            public Order getOrder() {
                return testOrder;
            }

            @Override
            public long getTimestamp() {
                return System.currentTimeMillis();
            }
        });
    }
}
