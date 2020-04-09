/*
 * Copyright 2020 the original author or authors.
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

package com.mallfoundry.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class InternalBillingAddress implements BillingAddress {

    @Column(name = "billing_address_consignee_")
    private String consignee;

    @JsonProperty("country_code")
    @Column(name = "billing_address_country_code_")
    private String countryCode;

    @Column(name = "billing_address_mobile_")
    private String mobile;

    @JsonProperty("postal_code")
    @Column(name = "billing_address_postal_code_")
    private String postalCode;

    @Column(name = "billing_address_address_")
    private String address;

    @Column(name = "billing_address_location_")
    private String location;

}
