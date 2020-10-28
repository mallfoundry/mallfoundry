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

package org.mallfoundry.catalog.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class ProductAttributeSupport implements ProductAttribute {

    public ProductAttributeSupport(String name, String value) {
        this.setName(name);
        this.setValue(value);
    }

    public ProductAttributeSupport(String namespace, String name, String value) {
        this(name, value);
        this.setNamespace(namespace);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final ProductAttributeSupport attribute;

        protected BuilderSupport(ProductAttributeSupport attribute) {
            this.attribute = attribute;
        }

        @Override
        public Builder namespace(String namespace) {
            this.attribute.setNamespace(namespace);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.attribute.setName(name);
            return this;
        }

        @Override
        public Builder value(String value) {
            this.attribute.setValue(value);
            return this;
        }

        @Override
        public Builder position(int position) {
            this.attribute.setPosition(position);
            return this;
        }

        @Override
        public ProductAttribute build() {
            return this.attribute;
        }
    }
}
