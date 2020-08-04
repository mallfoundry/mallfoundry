/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.store.repository.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.store.ProductCollection;
import org.mallfoundry.store.ProductCollectionSupport;
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
@Table(name = "mf_store_collection")
public class JpaProductCollection extends ProductCollectionSupport {

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
    private int position;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("created_time")
    @Column(name = "created_time_")
    private Date createdTime;

    public JpaProductCollection(String id) {
        this.id = id;
    }

    public JpaProductCollection(String storeId, String name) {
        this.storeId = storeId;
        this.name = name;
        this.setCreatedTime(new Date());
    }

    public static JpaProductCollection of(ProductCollection collection) {
        if (collection instanceof JpaProductCollection) {
            return (JpaProductCollection) collection;
        }
        var target = new JpaProductCollection();
        BeanUtils.copyProperties(collection, target);
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JpaProductCollection that = (JpaProductCollection) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
