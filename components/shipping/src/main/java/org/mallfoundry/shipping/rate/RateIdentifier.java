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

import org.mallfoundry.keygen.PrimaryKeyHolder;

import java.util.Objects;

public class RateIdentifier implements RateProcessor {

    private static final String RATE_ID_VALUE_NAME = "shipping.rate.id";

    private static final String ZONE_ID_VALUE_NAME = "shipping.rate.zone.id";

    @Override
    public Rate preProcessAddRate(Rate rate) {
        this.setIdentifier(rate);
        return rate;
    }

    @Override
    public Rate preProcessUpdateRate(Rate rate) {
        this.setIdentifier(rate);
        return rate;
    }

    private void setIdentifier(Rate rate) {
        if (Objects.isNull(rate.getId())) {
            rate.setId(PrimaryKeyHolder.next(RATE_ID_VALUE_NAME));
        }
        rate.getZones().forEach(zone -> {
            if (Objects.isNull(zone.getId())) {
                zone.setId(PrimaryKeyHolder.next(ZONE_ID_VALUE_NAME));
            }
        });
    }
}
