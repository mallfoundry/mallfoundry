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

package org.mallfoundry.catalog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.data.repository.jpa.convert.StringSetConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_brand")
public class InternalBrand implements Brand {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Column(name = "description_")
    private String description;

    @Column(name = "logo_url_")
    private String logoUrl;

    @ElementCollection
    @CollectionTable(name = "mf_brand_categories",
            joinColumns = @JoinColumn(name = "brand_id_"))
    @Column(name = "category_id_")
    private Set<String> categories;

    @Column(name = "search_keywords_")
    @Convert(converter = StringSetConverter.class)
    private Set<String> searchKeywords;

    @Column(name = "position_")
    private int position;

    public InternalBrand(String id) {
        this.id = id;
    }

    public static InternalBrand of(Brand brand) {
        if (brand instanceof InternalBrand) {
            return (InternalBrand) brand;
        }
        var target = new InternalBrand();
        BeanUtils.copyProperties(brand, target);
        return target;
    }
}
