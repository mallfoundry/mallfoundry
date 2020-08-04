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

package org.mallfoundry.util;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Copies {

    public static StringSetter notBlank(Supplier<String> supplier) {
        var value = supplier.get();
        return new StringSetter(value, StringUtils.isNotBlank(value));
    }

    public static class StringSetter {
        private final String value;
        private final boolean match;

        public StringSetter(String value, boolean match) {
            this.value = value;
            this.match = match;
        }

        public void trim(Consumer<String> consumer) {
            if (this.match) {
                consumer.accept(this.value);
            }
        }
    }
}
