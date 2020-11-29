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

import org.mallfoundry.util.ObjectBuilder;
import org.mallfoundry.util.Position;

import java.util.Set;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface Brand extends Position {

    String getId();

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getLogoUrl();

    void setLogoUrl(String logoUrl);

    Set<String> getCategories();

    void setCategories(Set<String> categories);

    Set<String> getSearchKeywords();

    void setSearchKeywords(Set<String> searchKeywords);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<Brand> {

        Builder name(String name);

        Builder description(String description);

        Builder logoUrl(String logoUrl);

        Builder categories(Set<String> categories);

        Builder searchKeywords(Set<String> searchKeywords);
    }

    abstract class BuilderSupport implements Builder {

        private final Brand brand;

        public BuilderSupport(Brand brand) {
            this.brand = brand;
        }

        @Override
        public Builder name(String name) {
            this.brand.setName(name);
            return this;
        }

        @Override
        public Builder description(String description) {
            this.brand.setDescription(description);
            return this;
        }

        @Override
        public Builder logoUrl(String logoUrl) {
            this.brand.setLogoUrl(logoUrl);
            return this;
        }

        @Override
        public Builder categories(Set<String> categories) {
            this.brand.setCategories(categories);
            return this;
        }

        @Override
        public Builder searchKeywords(Set<String> searchKeywords) {
            this.brand.setSearchKeywords(searchKeywords);
            return this;
        }

        @Override
        public Brand build() {
            return this.brand;
        }
    }
}
