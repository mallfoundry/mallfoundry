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
import org.mallfoundry.shipping.AddressSupport;
import org.mallfoundry.store.StoreAddress;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_store_address")
public class JpaStoreAddress extends AddressSupport implements StoreAddress {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "tenant_id_")
    private String tenantId;

    @Column(name = "first_name_")
    private String firstName;

    @Column(name = "last_name_")
    private String lastName;

    @Column(name = "country_code_")
    private String countryCode;

    @Column(name = "phone_")
    private String phone;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "type_")
    private AddressType type;

    @Column(name = "primary_")
    private boolean primary;

    @Column(name = "active_")
    private boolean active;

    public JpaStoreAddress(String id) {
        this.id = id;
    }

    public static JpaStoreAddress of(StoreAddress address) {
        if (address instanceof JpaStoreAddress) {
            return (JpaStoreAddress) address;
        }
        var target = new JpaStoreAddress();
        BeanUtils.copyProperties(address, target);
        return target;
    }
}
