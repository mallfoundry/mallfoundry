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

package org.mallfoundry.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_store")
public class InternalStore implements Store {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private StoreStatus status;

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
        this.setStatus(StoreStatus.ACTIVE);
        this.setCreatedTime(new Date());
    }
}
