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

package org.mallfoundry.district;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "district_cities")
public class InternalCity extends DistrictSupport implements City {

    @Column(name = "province_id_")
    private String provinceId;

    @OneToMany(targetEntity = InternalCounty.class)
    @JoinColumn(name = "city_id_")
    private List<County> counties = new ArrayList<>();

    public InternalCity(String id, String code, String name, String provinceId) {
        this.setId(id);
        this.setCode(code);
        this.setName(name);
        this.provinceId = provinceId;
    }

    public static InternalCity of(City city) {
        if (city instanceof InternalCity) {
            return (InternalCity) city;
        }
        var target = new InternalCity();
        BeanUtils.copyProperties(city, target);
        return target;
    }
}
