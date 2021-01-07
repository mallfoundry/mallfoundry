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

package org.mallfoundry.geography;

public abstract class GeographySupport implements Geography {

    protected abstract static class BuilderSupport<D extends Geography, B> implements Geography.BuilderBase<D, B> {

        private final Geography geography;

        protected BuilderSupport(Geography geography) {
            this.geography = geography;
        }

        @SuppressWarnings("unchecked")
        @Override
        public B id(String id) {
            this.geography.setId(id);
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public B code(String code) {
            this.geography.setCode(code);
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public B name(String name) {
            this.geography.setName(name);
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public B position(int position) {
            this.geography.setPosition(position);
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public D build() {
            return (D) this.geography;
        }
    }

}
