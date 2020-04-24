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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "district_counties")
public class InternalCounty extends DistrictSupport implements County {

    @Column(name = "city_id_")
    private String cityId;

    public InternalCounty(String id, String code, String name, String cityId) {
        this.setId(id);
        this.setCode(code);
        this.setName(name);
        this.cityId = cityId;
    }

    public static InternalCounty of(County county) {
        if (county instanceof InternalCounty) {
            return (InternalCounty) county;
        }
        var target = new InternalCounty();
        BeanUtils.copyProperties(county, target);
        return target;
    }
}
