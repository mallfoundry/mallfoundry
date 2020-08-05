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

package org.mallfoundry.customer.repository.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.customer.CustomerAddress;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_customer_address")
public class JpaCustomerAddress implements CustomerAddress {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "first_name_")
    private String firstName;

    @Column(name = "last_name_")
    private String lastName;

    @Column(name = "tag_")
    private String tag;

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

    @Column(name = "defaulted_")
    private boolean defaulted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_time_")
    private Date createdTime;

    public JpaCustomerAddress(String id) {
        this.id = id;
        this.createdTime = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JpaCustomerAddress that = (JpaCustomerAddress) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
