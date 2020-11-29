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
import org.junit.jupiter.api.Test;
import org.mallfoundry.shipping.rate.repository.jpa.JpaZone;
import org.mallfoundry.test.StaticTest;
import org.mallfoundry.util.DecimalUtils;

import java.math.BigDecimal;

@StaticTest
public class ZoneTests {

    @Test
    public void testCalculateCost1() {
        var zone = new JpaZone().toBuilder().first(10).firstCost(11).additional(2).additionalCost(2).build();
        var cost1 = zone.calculateCost(BigDecimal.valueOf(10));
        Assertions.assertThat(DecimalUtils.equals(cost1, BigDecimal.valueOf(11))).isTrue();
    }

    @Test
    public void testCalculateCost2() {
        var zone = new JpaZone().toBuilder().first(10).firstCost(11).additional(2).additionalCost(2).build();
        var cost2 = zone.calculateCost(BigDecimal.valueOf(11));
        Assertions.assertThat(DecimalUtils.equals(cost2, BigDecimal.valueOf(13))).isTrue();
    }

    @Test
    public void testCalculateCost3() {
        var zone = new JpaZone().toBuilder().first(10).firstCost(11).additional(2).additionalCost(2).build();
        var cost3 = zone.calculateCost(BigDecimal.valueOf(12));
        Assertions.assertThat(DecimalUtils.equals(cost3, BigDecimal.valueOf(13))).isTrue();
    }

    @Test
    public void testCalculateCost4() {
        var zone = new JpaZone().toBuilder().first(10).firstCost(11).additional(2).additionalCost(2).build();
        var cost4 = zone.calculateCost(BigDecimal.valueOf(13));
        Assertions.assertThat(DecimalUtils.equals(cost4, BigDecimal.valueOf(15))).isTrue();
    }
}
