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

import org.mallfoundry.util.DecimalUtils;

import java.math.BigDecimal;
import java.util.List;

public abstract class ZoneSupport implements Zone {

    @Override
    public boolean containsLocation(String location) {
        return this.getLocations().contains(location);
    }

    @Override
    public BigDecimal calculateCost(BigDecimal baseAmount) {
        var amount = baseAmount.subtract(this.getFirst());
        var cost = this.getFirstCost();
        // 0 < amount
        while (DecimalUtils.lessThan(BigDecimal.ZERO, amount)) {
            amount = amount.subtract(this.getAdditional());
            cost = cost.add(this.getAdditionalCost());
        }
        return cost;
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final ZoneSupport zone;

        protected BuilderSupport(ZoneSupport zone) {
            this.zone = zone;
        }

        @Override
        public Builder name(String name) {
            this.zone.setName(name);
            return this;
        }

        @Override
        public Builder locations(List<String> locations) {
            this.zone.setLocations(locations);
            return this;
        }

        @Override
        public Builder first(BigDecimal firstAmount) {
            this.zone.setFirst(firstAmount);
            return this;
        }

        @Override
        public Builder firstCost(BigDecimal firstCost) {
            this.zone.setFirstCost(firstCost);
            return this;
        }

        @Override
        public Builder additional(BigDecimal additionalAmount) {
            this.zone.setAdditional(additionalAmount);
            return this;
        }

        @Override
        public Builder additionalCost(BigDecimal additionalCost) {
            this.zone.setAdditionalCost(additionalCost);
            return this;
        }

        @Override
        public Builder first(double firstAmount) {
            return this.first(BigDecimal.valueOf(firstAmount));
        }

        @Override
        public Builder firstCost(double firstCost) {
            return this.firstCost(BigDecimal.valueOf(firstCost));
        }

        @Override
        public Builder additional(double additionalAmount) {
            return this.additional(BigDecimal.valueOf(additionalAmount));
        }

        @Override
        public Builder additionalCost(double additionalCost) {
            return this.additionalCost(BigDecimal.valueOf(additionalCost));
        }

        @Override
        public Zone build() {
            return this.zone;
        }
    }
}
