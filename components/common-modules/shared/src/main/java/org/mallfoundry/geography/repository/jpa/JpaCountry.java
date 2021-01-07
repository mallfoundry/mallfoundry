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

package org.mallfoundry.geography.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.geography.Country;
import org.mallfoundry.geography.CountrySupport;
import org.mallfoundry.geography.Province;
import org.mallfoundry.geography.Region;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "mf_district_country")
public class JpaCountry extends CountrySupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "code_")
    private String code;

    @Column(name = "name_")
    private String name;

    @Column(name = "position_")
    private int position;

    @OneToMany(targetEntity = JpaProvince.class)
    @JoinColumn(name = "country_id_")
    @OrderBy("position")
    private List<Province> provinces = new ArrayList<>();

    public JpaCountry(String id) {
        this.setId(id);
    }

    public static JpaCountry of(Country country) {
        if (country instanceof JpaCountry) {
            return (JpaCountry) country;
        }
        var target = new JpaCountry();
        BeanUtils.copyProperties(country, target);
        return target;
    }

    @Override
    public Region createRegion(String regionId) {
        return new JpaRegion(regionId, this.id);
    }

    @Override
    public Province createProvince(String provinceId) {
        return new JpaProvince(provinceId, this.id);
    }
}
