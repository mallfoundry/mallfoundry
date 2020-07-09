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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_shipping_rate")
public class InternalRate implements Rate {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "name_")
    private String name;

    @Column(name = "mode_")
    private RateMode mode;

    @OneToMany(targetEntity = InternalZone.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "rate_id_")
    private List<Zone> zones = new ArrayList<>();

    @Column(name = "enabled_")
    private boolean enabled;

    public InternalRate(String id, String storeId) {
        this.id = id;
        this.storeId = storeId;
    }

    public static InternalRate of(Rate rate) {
        if (rate instanceof InternalRate) {
            return (InternalRate) rate;
        }
        var target = new InternalRate();
        BeanUtils.copyProperties(rate, target);
        return target;
    }

    @Override
    public BigDecimal getMinimumCost() {
        return this.getZones().stream()
                .map(Zone::getFirstCost)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getMaximumCost() {
        return this.getZones().stream()
                .map(Zone::getFirstCost)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public void addZone(Zone zone) {
        this.getZones().add(zone);
    }
}
