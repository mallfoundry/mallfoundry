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
import org.mallfoundry.data.jpa.convert.StringSetConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_catalog_category")
public class InternalCategory implements Category {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Convert(converter = StringSetConverter.class)
    @Column(name = "search_keywords_")
    private Set<String> searchKeywords;

    @Column(name = "image_url_")
    private String imageUrl;

    @Column(name = "description_")
    private String description;

    @Column(name = "parent_id_")
    private String parentId;

    @OneToMany(targetEntity = InternalCategory.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id_")
    @OrderBy("position ASC")
    private List<Category> children;

    @Enumerated
    @Column(name = "visibility_")
    private CategoryVisibility visibility;

    @Column(name = "position_")
    private int position;

    public InternalCategory(String id) {
        this.id = id;
    }

    public static InternalCategory of(Category category) {
        if (category instanceof InternalCategory) {
            return (InternalCategory) category;
        }
        var target = new InternalCategory();
        BeanUtils.copyProperties(category, target);
        return target;
    }

    @Override
    public Category createCategory() {
        return new InternalCategory();
    }

    @Override
    public void addCategory(Category category) {
        this.children.add(category);
    }

    @Override
    public void removeCategory(Category category) {
        this.children.remove(category);
    }

    @Override
    public void show() {
        this.visibility = CategoryVisibility.VISIBLE;
    }

    @Override
    public void hide() {
        this.visibility = CategoryVisibility.HIDDEN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternalCategory category = (InternalCategory) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
