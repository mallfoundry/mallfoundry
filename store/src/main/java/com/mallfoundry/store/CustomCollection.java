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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "position", "createdTime"})
@Entity
@Table(name = "store_custom_collection")
public class CustomCollection implements Comparable<CustomCollection> {

    @JsonIgnore
    private StoreId storeId;

    @Id
    @GeneratedValue
    @Column(name = "id_")
    private Long id;

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

    public CustomCollection(StoreId storeId, String name) {
        this.setStoreId(storeId);
        this.setName(name);
    }

    @Embedded
    public StoreId getStoreId() {
        return storeId;
    }

    public void create() {
        this.setCreatedTime(new Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomCollection that = (CustomCollection) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(CustomCollection o) {
        int selfPosition = Objects.isNull(this.getPosition()) ? Integer.MAX_VALUE : this.getPosition();
        int otherPosition = Objects.isNull(o.getPosition()) ? Integer.MAX_VALUE : o.getPosition();
        return Integer.compare(selfPosition, otherPosition);
    }
}