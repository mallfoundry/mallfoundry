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

package org.mallfoundry.shipping;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mallfoundry.shipping.rate.RateMode;
import org.mallfoundry.shipping.rate.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RateTests {

    @Autowired
    private RateService rateService;

    @Test
    @Rollback(false)
    @Transactional
    public void testCreateRate() {
        var zone = rateService.createZone();
        zone.setLocations(List.of("370832", "370831", "370833"));
        zone.setName("test");
        zone.setFirst(BigDecimal.valueOf(1));
        zone.setFirstCost(BigDecimal.valueOf(10));
        zone.setAdditional(BigDecimal.valueOf(1));
        zone.setAdditionalCost(BigDecimal.valueOf(5));
        var rate = rateService.createRate("mi");
        rate.addZone(zone);
        rate.setMode(RateMode.PACKAGE);
        rateService.saveRate(rate);
    }

    @Test
    @Rollback(false)
    @Transactional
    public void testGetRate() {
        var rate = rateService.getRate("3").orElseThrow();
//        rate.
        System.out.println(rate);
    }
}
