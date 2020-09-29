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

package org.mallfoundry.edw.stream;

import org.junit.jupiter.api.Test;
import org.mallfoundry.edw.order.OrderFactManager;
import org.mallfoundry.edw.time.DateDimensionManager;
import org.mallfoundry.edw.time.TimeDimensionManager;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@StandaloneTest
public class OrderFactManagerTests {
    @Autowired
    private DateDimensionManager dateDimensionManager;

    @Autowired
    private TimeDimensionManager timeDimensionManager;

    @Autowired
    private OrderFactManager orderFactManager;

    public Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public void addOrderFact(Date date, BigDecimal amount) {
        var factKey = this.orderFactManager.createOrderFactKey();
        factKey.setKey(UUID.randomUUID().toString());
        factKey.setTenantKey("0");
        factKey.setStoreKey("mi");
        factKey.setCustomerKey("");
        factKey.setPlacedDateKey(this.dateDimensionManager.createDateDimensionKey(date));
        factKey.setPlacedTimeKey(this.timeDimensionManager.createTimeDimensionKey(date));
        factKey.setPaidDateKey(this.dateDimensionManager.createDateDimensionKey(date));
        factKey.setPaidTimeKey(this.timeDimensionManager.createTimeDimensionKey(date));
        var fact = this.orderFactManager.createOrderFact(factKey);
        fact.setTotalPrice(amount);
        fact.setTotalQuantity(1);
        fact.setTotalAmount(amount);
        this.orderFactManager.saveOrderFacts(List.of(fact));
    }

    @Test
    public void testAdd() {
        var startTime = LocalDateTime.of(2020, 9, 1, 1, 1, 1);
        var endTime = LocalDateTime.of(2020, 9, 30, 1, 1, 1);

        var r = new Random();
        while (startTime.isBefore(endTime)) {
            var rd = 1;
            var rr = 10 + r.nextInt(100);
            while (rd <= rr) {
                this.addOrderFact(this.localDateTime2Date(startTime), BigDecimal.valueOf(rr));
                rd++;
            }
            startTime = startTime.plusDays(1);
        }
    }
}
