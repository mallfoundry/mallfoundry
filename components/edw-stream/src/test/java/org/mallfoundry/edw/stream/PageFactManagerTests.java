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
import org.mallfoundry.edw.page.PageFactManager;
import org.mallfoundry.edw.time.DateDimensionManager;
import org.mallfoundry.edw.time.TimeDimensionManager;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Random;

@StandaloneTest
public class PageFactManagerTests {

    @Autowired
    private DateDimensionManager dateDimensionManager;

    @Autowired
    private TimeDimensionManager timeDimensionManager;

    @Autowired
    private PageFactManager pageFactManager;


    public Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        return Date.from(zdt.toInstant());
    }


    public void addPageFact(String browserId, String browserIp, String pageId, Date date) {
        var factKey = this.pageFactManager.createPageFactKey();
//        factKey.setTenantKey(view.getTenantId());
//        factKey.setStoreKey(view.getStoreId());
        factKey.setTenantKey("0");
        factKey.setStoreKey("mi");
        factKey.setBrowserKey(browserId);
        factKey.setBrowserIpKey(browserIp);
        factKey.setPageKey(pageId);
        factKey.setDateKey(this.dateDimensionManager.createDateDimensionKey(date));
        factKey.setTimeKey(this.timeDimensionManager.createTimeDimensionKey(date));
        var fact = this.pageFactManager.createPageFact(factKey);
        fact.setViewCount(1);
        this.pageFactManager.savePageFact(fact);
    }

    @Test
    public void testAdd() {
        var startTime = LocalDateTime.of(2020, 9, 1, 1, 1, 1);
        var endTime = LocalDateTime.of(2020, 10, 1, 1, 1, 1);
        var r = new Random();
        while (startTime.isBefore(endTime)) {
            var rd = 1;
            var rr = r.nextInt(20);
            while (rd <= rr) {
                this.addPageFact("1", "1", "1", this.localDateTime2Date(startTime));
                rd++;
            }
            startTime = startTime.plusDays(1);
        }
//        this.eventPublisher.publishEvent(new Im);
    }
}
