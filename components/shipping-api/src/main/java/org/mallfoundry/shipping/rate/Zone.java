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

import org.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.List;

public interface Zone extends ObjectBuilder.ToBuilder<Zone.Builder> {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    List<String> getLocations();

    void setLocations(List<String> locations);

    boolean containsLocation(String location);

    BigDecimal getFirstCost();

    void setFirstCost(BigDecimal firstCost);

    BigDecimal getFirst();

    void setFirst(BigDecimal amount);

    BigDecimal getAdditional();

    void setAdditional(BigDecimal amount);

    BigDecimal getAdditionalCost();

    void setAdditionalCost(BigDecimal cost);

    BigDecimal calculateCost(BigDecimal amount);

    interface Builder extends ObjectBuilder<Zone> {

        Builder name(String name);

        Builder locations(List<String> locations);

        Builder first(BigDecimal firstAmount);

        Builder firstCost(BigDecimal firstCost);

        Builder additional(BigDecimal additionalAmount);

        Builder additionalCost(BigDecimal additionalCost);

        Builder first(double firstAmount);

        Builder firstCost(double firstCost);

        Builder additional(double additionalAmount);

        Builder additionalCost(double additionalCost);
    }
}
