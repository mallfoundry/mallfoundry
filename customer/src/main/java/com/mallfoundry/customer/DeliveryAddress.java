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

package com.mallfoundry.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer_delivery_address")
public class DeliveryAddress {

    @Id
    @GeneratedValue
    @Column(name = "id_")
    private Long id;

    @Column(name = "consignee_")
    private String consignee;

    @Column(name = "mobile_")
    private String mobile;

    @JsonProperty("postal_code")
    @Column(name = "postal_code_")
    private String postalCode;

    @Column(name = "country_")
    private String country;

    @Column(name = "location_")
    private String location;

    @Column(name = "address_")
    private String address;

    @Column(name = "defaulted_")
    private boolean defaulted;

    @JsonProperty("added_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "added_time_")
    private Date addedTime;

    public void nowAddedTime() {
        this.setAddedTime(new Date());
    }

    public void defaulted() {
        this.setDefaulted(true);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryAddress that = (DeliveryAddress) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Builder {

        private final DeliveryAddress address;

        public Builder() {
            this(new DeliveryAddress());
        }

        public Builder(DeliveryAddress address) {
            this.address = address;
        }

        public Builder consignee(String consignee) {
            this.address.setConsignee(consignee);
            return this;
        }

        public Builder mobile(String mobile) {
            this.address.setMobile(mobile);
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.address.setPostalCode(postalCode);
            return this;
        }

        public Builder country(String country) {
            this.address.setCountry(country);
            return this;
        }

        public Builder location(String location) {
            this.address.setLocation(location);
            return this;
        }

        public Builder address(String address) {
            this.address.setAddress(address);
            return this;
        }

        public Builder defaulted() {
            this.address.defaulted();
            return this;
        }

        public DeliveryAddress build() {
            this.address.nowAddedTime();
            return this.address;
        }
    }
}
