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
import java.util.List;
import java.util.Optional;

/**
 * 运费价格对象，用于计算不同地区，不同数量的商品价格。
 *
 * @author Zhi Tang
 */
public interface Rate {

    String getId();

    String getStoreId();

    String getName();

    void setName(String name);

    RateMode getMode();

    void setMode(RateMode mode);

    Zone createZone(String zoneId);

    Optional<Zone> getZone(String zoneId);

    List<Zone> getZones();

    void setZones(List<Zone> zones);

    void addZone(Zone zone);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    BigDecimal getMinimumCost();

    BigDecimal getMaximumCost();

    /**
     * 根据发货地址和数额计算报价。
     *
     * @param address 地址对象
     * @param amount  数额
     * @return 报价
     */
    BigDecimal calculateQuote(Address address, BigDecimal amount);
}
