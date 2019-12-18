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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "position", "children"})
@Entity
@Table(name = "store_custom_collection")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_")
public class CustomCollection implements Comparable<CustomCollection> {

    @JsonIgnore
    private StoreId storeId;

    @Id
    @GeneratedValue
    @Column(name = "id_")
    private Integer id;

    @Column(name = "name_")
    private String name;

    @Column(name = "position_")
    private Integer position;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id_")
    private List<ChildCustomCollection> children = new ArrayList<>();

    public CustomCollection(String name) {
        this.setName(name);
    }

    public CustomCollection(StoreId storeId, String name) {
        this.setStoreId(storeId);
        this.setName(name);
    }

    @Embedded
    public StoreId getStoreId() {
        return storeId;
    }

    public ChildCustomCollection createChildCollection(String name) {
        return new ChildCustomCollection(this, name);
    }

    public void addChildCollection(ChildCustomCollection collection) {
        collection.setPosition(Integer.MAX_VALUE);
        collection.setParent(this);
        this.getChildren().add(collection);
        CustomCollectionPositions.sort(this.getChildren());
    }

    public void removeChildCollection(ChildCustomCollection collection) {
        this.getChildren().remove(collection);
    }

    public void clearChildCollections() {
        this.getChildren().clear();
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