/*
 * Copyright (C) 2019-2021 the original author or authors.
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

package org.mallfoundry.catalog.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.Category;
import org.mallfoundry.catalog.CategorySupport;
import org.mallfoundry.catalog.CategoryVisibility;
import org.mallfoundry.data.repository.jpa.convert.StringSetConverter;
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
public class JpaCategory extends CategorySupport {

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

    @OneToMany(targetEntity = JpaCategory.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id_")
    @OrderBy("position ASC")
    private List<Category> children;

    @Enumerated
    @Column(name = "visibility_")
    private CategoryVisibility visibility;

    @Column(name = "position_")
    private int position;

    public JpaCategory(String id) {
        this.id = id;
    }

    public static JpaCategory of(Category category) {
        if (category instanceof JpaCategory) {
            return (JpaCategory) category;
        }
        var target = new JpaCategory();
        BeanUtils.copyProperties(category, target);
        return target;
    }

    @Override
    public Category createCategory() {
        return new JpaCategory();
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
        JpaCategory category = (JpaCategory) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
