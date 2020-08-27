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

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCarrierService implements CarrierService {

    @Override
    public Carrier createCarrier(String code, String name) {
        return null;
    }

    @Override
    public List<Carrier> getCarriers() {
        return List.of(
                new DefaultCarrier("申通快递", CarrierCode.STO),
                new DefaultCarrier("中通快递", CarrierCode.ZTO),
                new DefaultCarrier("圆通快递", CarrierCode.YTO));
    }

    @Override
    public void addCarrier(Carrier carrier) {

    }

    @Override
    public Carrier getCarrier(CarrierCode code) {
        return this.getCarriers().stream().filter(carrier -> carrier.getCode().equals(code)).findFirst().orElseThrow();
    }
}
