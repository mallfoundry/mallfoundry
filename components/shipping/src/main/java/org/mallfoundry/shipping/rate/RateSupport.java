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

import org.mallfoundry.shipping.Address;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public abstract class RateSupport implements Rate {

    @Override
    public Optional<Zone> getZone(String zoneId) {
        return this.getZones().stream().filter(zone -> Objects.equals(zone.getId(), zoneId)).findFirst();
    }

    @Override
    public void addZone(Zone zone) {
        this.getZones().add(zone);
    }

    private Zone requiredZone(Address address) {
        // 根据地址的县、市、省，由小到大来获得地区对象。
        var locations = new ArrayList<String>();
        if (Objects.nonNull(address.getCountyId())) {
            locations.add(address.getCountyId());
        }
        if (Objects.nonNull(address.getCityId())) {
            locations.add(address.getCityId());
        }
        if (Objects.nonNull(address.getProvinceId())) {
            locations.add(address.getProvinceId());
        }
        for (var location : locations) {
            for (var zone : this.getZones()) {
                if (zone.containsLocation(location)) {
                    return zone;
                }
            }
        }
        throw new RateException(RateMessages.addressNotSupported());
    }

    @Override
    public BigDecimal calculateQuote(Address address, BigDecimal amount) {
        return this.requiredZone(address).calculateCost(amount);
    }
}
