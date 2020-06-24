/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mallfoundry.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_customer")
public class InternalCustomer implements Customer {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "username_")
    private String username;

    @Column(name = "avatar_")
    private String avatar;

    @Column(name = "nickname_")
    private String nickname;

    @Column(name = "gender_")
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate_")
    private Date birthdate;

    @OneToMany(targetEntity = InternalCustomerAddress.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id_")
    @OrderBy("defaulted DESC , createdTime ASC")
    private List<CustomerAddress> addresses = new ArrayList<>();

    public InternalCustomer(String userId) {
        this.id = userId;
        this.gender = Gender.UNKNOWN;
        this.birthdate = new Date();
    }

    public static InternalCustomer of(Customer customer) {
        if (customer instanceof InternalCustomer) {
            return (InternalCustomer) customer;
        }
        var target = new InternalCustomer();
        BeanUtils.copyProperties(customer, target);
        return target;
    }

    @Override
    public Optional<CustomerAddress> getDefaultAddress() {
        return this.addresses.stream().filter(CustomerAddress::isDefaulted).findFirst();
    }

    @Override
    public Optional<CustomerAddress> getAddress(String addressId) {
        return this.addresses.stream().filter(address -> Objects.equals(address.getId(), addressId)).findFirst();
    }

    @Override
    public CustomerAddress createAddress(String id) {
        return new InternalCustomerAddress(id);
    }

    @Override
    public void addAddress(final CustomerAddress address) {
        this.addresses.remove(address);
        this.addresses.add(address);
        this.switchDefaultAddress(address);
    }

    private void switchDefaultAddress(CustomerAddress address) {
        if (address.isDefaulted()) {
            this.getDefaultAddress()
                    .ifPresent(defaultAddress -> {
                        if (!Objects.equals(defaultAddress, address)) {
                            defaultAddress.setDefaulted(false);
                        }
                    });
        }
    }

    @Override
    public void setAddress(CustomerAddress address) {
        var oldAddress = this.getAddress(address.getId()).orElseThrow();

        var firstName = StringUtils.trim(address.getFirstName());
        if (StringUtils.isNotEmpty(firstName)) {
            oldAddress.setFirstName(firstName);
        }

        var lastName = StringUtils.trim(address.getLastName());
        if (StringUtils.isNotEmpty(lastName)) {
            oldAddress.setLastName(lastName);
        }

        var countryCode = StringUtils.trim(address.getCountryCode());
        if (StringUtils.isNotEmpty(countryCode)) {
            oldAddress.setCountryCode(countryCode);
        }

        var provinceId = StringUtils.trim(address.getProvinceId());
        var province = StringUtils.trim(address.getProvince());
        if (StringUtils.isNotEmpty(provinceId)
                && StringUtils.isNotEmpty(province)) {
            oldAddress.setProvinceId(provinceId);
            oldAddress.setProvince(province);
        }

        var cityId = StringUtils.trim(address.getCityId());
        var city = StringUtils.trim(address.getCity());
        if (StringUtils.isNotEmpty(cityId)
                && StringUtils.isNotEmpty(city)) {
            oldAddress.setCityId(cityId);
            oldAddress.setCity(city);
        }

        var countyId = StringUtils.trim(address.getCountyId());
        var county = StringUtils.trim(address.getCounty());
        if (StringUtils.isNotEmpty(countyId)
                && StringUtils.isNotEmpty(county)) {
            oldAddress.setCountyId(countyId);
            oldAddress.setCounty(county);
        }

        var anAddress = StringUtils.trim(address.getAddress());
        if (StringUtils.isNotEmpty(anAddress)) {
            oldAddress.setAddress(anAddress);
        }

        var mobile = StringUtils.trim(address.getMobile());
        if (StringUtils.isNotEmpty(mobile)) {
            oldAddress.setMobile(mobile);
        }

        var tag = StringUtils.trim(address.getTag());
        if (StringUtils.isNotEmpty(tag)) {
            oldAddress.setTag(tag);
        }
        var zip = StringUtils.trim(address.getZip());
        if (StringUtils.isNotEmpty(zip)) {
            oldAddress.setZip(zip);
        }

        if (address.isDefaulted()) {
            oldAddress.setDefaulted(true);
            this.switchDefaultAddress(oldAddress);
        }
    }

    @Override
    public void removeAddress(CustomerAddress address) {
        this.addresses.remove(address);
    }
}
