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

package org.mallfoundry.shipping.rate;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.mallfoundry.shipping.DefaultAddress;
import org.mallfoundry.shipping.rate.repository.jpa.JpaRate;
import org.mallfoundry.test.StaticTest;
import org.mallfoundry.util.DecimalUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@StaticTest
public class RateTests {

    @Test
    public void testCalculateQuote() {
        var rate = new JpaRate();
        rate.addZone(rate.createZone("1").toBuilder().locations(List.of("1", "2")).first(10).firstCost(11).additional(2).additionalCost(2).build());
        var address = new DefaultAddress().toBuilder().cityId("1").countyId("2").provinceId("3").build();
        var quote = rate.calculateQuote(address, BigDecimal.valueOf(10));
        var equals = DecimalUtils.equals(quote, BigDecimal.valueOf(11));
        Assertions.assertThat(equals).isTrue();
    }

    @Test
    public void testAddCalculateQuote() {
        var rate = new JpaRate();
        rate.addZone(rate.createZone("1").toBuilder().locations(List.of("1", "2")).first(10).firstCost(11).additional(2).additionalCost(2).build());
        var address = new DefaultAddress().toBuilder().cityId("1").countyId("2").provinceId("3").build();
        var quote = rate.calculateQuote(address, BigDecimal.valueOf(10));
        var equals = DecimalUtils.equals(quote, BigDecimal.valueOf(11));
        Assertions.assertThat(equals).isTrue();
    }
}
