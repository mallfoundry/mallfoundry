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

package com.mallfoundry.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stores")
public class InternalStore implements Store {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Column(name = "domain_")
    private String domain;

    @JsonProperty("logo_url")
    @Column(name = "logo_url_")
    private String logoUrl;

    @JsonProperty("owner_id")
    @Column(name = "owner_id_")
    private String ownerId;

    @Column(name = "industry_")
    private String industry;

    @Column(name = "description_")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "created_time", access = JsonProperty.Access.READ_ONLY)
    @Column(name = "created_time_")
    private Date createdTime;

    public InternalStore(String id) {
        this.setId(id);
    }

    public static InternalStore of(Store store) {
        if (store instanceof InternalStore) {
            return (InternalStore) store;
        }

        var target = new InternalStore();
        BeanUtils.copyProperties(store, target);
        return target;
    }

    @Override
    public void initialize() {
        this.setCreatedTime(new Date());
    }
}
