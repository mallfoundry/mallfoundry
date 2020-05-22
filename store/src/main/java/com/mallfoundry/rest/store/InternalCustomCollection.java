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

package com.mallfoundry.rest.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "store_custom_collections")
public class InternalCustomCollection implements CustomCollection {

    @Id
    @Column(name = "id_")
    private String id;

    @JsonIgnore
    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "name_")
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "products_")
    private int products;

    @Column(name = "position_")
    private Integer position;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("created_time")
    @Column(name = "created_time_")
    private Date createdTime;

    public InternalCustomCollection(String id, String storeId, String name) {
        this.id = id;
        this.storeId = storeId;
        this.name = name;
        this.setCreatedTime(new Date());
    }

    public static InternalCustomCollection of(CustomCollection collection) {
        if (collection instanceof InternalCustomCollection) {
            return (InternalCustomCollection) collection;
        }
        var target = new InternalCustomCollection();
        BeanUtils.copyProperties(collection, target);
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalCustomCollection that = (InternalCustomCollection) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}