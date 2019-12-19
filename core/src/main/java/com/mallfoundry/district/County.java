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

package com.mallfoundry.district;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
@DiscriminatorValue("county")
public class County extends District {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "province_code_")
    private Province province;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "city_code_")
    private City city;

    @JsonProperty("province_code")
    private String getProvinceCode() {
        return this.getProvince().getCode();
    }

    @JsonProperty("city_code")
    private String getCityCode() {
        return this.getCity().getCode();
    }
}
