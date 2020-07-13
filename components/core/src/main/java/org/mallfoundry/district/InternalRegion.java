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

package org.mallfoundry.district;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_district_region")
public class InternalRegion extends DistrictSupport implements Region {

    @Column(name = "country_id_")
    private String countryId;

    @OneToMany(targetEntity = InternalProvince.class)
    @JoinColumn(name = "region_id_")
    @OrderBy("position")
    private List<Province> provinces = new ArrayList<>();

    public InternalRegion(String code, String name, String countryId) {
        this.setCode(code);
        this.setName(name);
        this.countryId = countryId;
    }

    public static InternalRegion of(Region region) {
        if (region instanceof InternalRegion) {
            return (InternalRegion) region;
        }
        var target = new InternalRegion();
        BeanUtils.copyProperties(region, target);
        return target;
    }
}
