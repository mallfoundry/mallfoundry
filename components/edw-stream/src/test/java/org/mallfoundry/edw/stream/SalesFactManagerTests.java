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
import org.mallfoundry.edw.sales.SalesFactManager;
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

@StandaloneTest
public class SalesFactManagerTests {

    @Autowired
    private DateDimensionManager dateDimensionManager;

    @Autowired
    private TimeDimensionManager timeDimensionManager;

    @Autowired
    private SalesFactManager salesFactManager;


    public Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        return Date.from(zdt.toInstant());
    }


    public void addSalesFact(Date date, String productKey, BigDecimal amount) {
        var factKey = this.salesFactManager.createSalesFactKey();
        factKey.setTenantKey("1");
        factKey.setStoreKey("mi");
        factKey.setCustomerKey("1");
        factKey.setProductKey(productKey);
        factKey.setVariantKey("1");
        factKey.setDateKey(this.dateDimensionManager.createDateDimensionKey(date));
        factKey.setTimeKey(this.timeDimensionManager.createTimeDimensionKey(date));
        var fact = this.salesFactManager.createSalesFact(factKey);
        fact.setSalesQuantity(2);
        fact.setSalesAmount(amount);
        this.salesFactManager.saveSalesFacts(List.of(fact));
    }

    @Test
    public void testAdd() {
        var productKey = List.of(317, 320, 316, 318, 315, 321, 322);
        var startTime = LocalDateTime.of(2020, 1, 1, 1, 1, 1);

        var endTime = LocalDateTime.of(2020, 12, 30, 1, 1, 1);

        var r = new Random();
        while (startTime.isBefore(endTime)) {
            var rd = 1;
            var rr = r.nextInt(20);
            while (rd <= rr) {
                this.addSalesFact(this.localDateTime2Date(startTime), productKey.get(r.nextInt(productKey.size())).toString(), BigDecimal.valueOf(rr));
                rd++;
            }

            startTime = startTime.plusDays(1);

        }
//        this.eventPublisher.publishEvent(new Im);
    }
}
