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

public interface CategoryQuery {

    String getParentId();

    void setParentId(String parentId);

    int getLevel();

    void setLevel(int level);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<CategoryQuery> {

        Builder parentId(String parentId);

        Builder level(int level);
    }

    abstract class BuilderSupport implements Builder {

        protected final CategoryQuery query;

        public BuilderSupport(CategoryQuery query) {
            this.query = query;
        }

        @Override
        public Builder parentId(String parentId) {
            this.query.setParentId(parentId);
            return this;
        }

        @Override
        public Builder level(int level) {
            this.query.setLevel(level);
            return this;
        }

        @Override
        public CategoryQuery build() {
            return this.query;
        }
    }
}
