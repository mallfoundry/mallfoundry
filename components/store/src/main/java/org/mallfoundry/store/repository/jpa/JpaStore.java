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

package org.mallfoundry.store.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.store.Store;
import org.mallfoundry.store.StoreStatus;
import org.mallfoundry.store.StoreSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_store")
public class JpaStore extends StoreSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private StoreStatus status;

    @Column(name = "domain_")
    private String domain;

    @Column(name = "logo_")
    private String logo;

    @Column(name = "owner_id_")
    private String ownerId;

    @Column(name = "industry_")
    private String industry;

    @Column(name = "description_")
    private String description;

    @Column(name = "country_code_")
    private String countryCode;

    @Column(name = "mobile_")
    private String mobile;

    @Column(name = "zip_")
    private String zip;

    @Column(name = "province_id_")
    private String provinceId;

    @Column(name = "province_")
    private String province;

    @Column(name = "city_id_")
    private String cityId;

    @Column(name = "city_")
    private String city;

    @Column(name = "county_id_")
    private String countyId;

    @Column(name = "county_")
    private String county;

    @Column(name = "address_")
    private String address;

    @Column(name = "created_time_")
    private Date createdTime;

    public JpaStore(String id) {
        this.setId(id);
    }

    public static JpaStore of(Store store) {
        if (store instanceof JpaStore) {
            return (JpaStore) store;
        }

        var target = new JpaStore();
        BeanUtils.copyProperties(store, target);
        return target;
    }

    @Override
    public void initialize() {
        this.setStatus(StoreStatus.ACTIVE);
        this.setCreatedTime(new Date());
    }
}