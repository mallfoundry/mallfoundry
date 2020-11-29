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

package org.mallfoundry.analytics.schema;

public abstract class ObjectFieldSupport implements ObjectField {

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final ObjectFieldSupport field;

        BuilderSupport(ObjectFieldSupport field) {
            this.field = field;
        }

        @Override
        public Builder name(String name) {
            this.field.setName(name);
            return this;
        }

        @Override
        public Builder label(String label) {
            this.field.setLabel(label);
            return this;
        }

        @Override
        public Builder type(String type) {
            this.field.setType(type);
            return this;
        }

        @Override
        public ObjectField build() {
            return this.field;
        }
    }
}
