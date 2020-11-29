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
import org.springframework.data.util.CastUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Copies {

    public static CopyObject notBlank(Supplier<String> supplier) {
        return new CopyObject().notBlank(supplier);
    }

    public static CopyObject notNull(Supplier<Object> supplier) {
        return new CopyObject().notNull(supplier);
    }

    public static class CopyObject {
        private Object value;
        private boolean match;

        public CopyObject notBlank(Supplier<String> supplier) {
            var value = supplier.get();
            this.match = StringUtils.isNotBlank(value);
            this.value = value;
            return this;
        }

        public CopyObject notNull(Supplier<Object> supplier) {
            var value = supplier.get();
            this.match = Objects.nonNull(value);
            this.value = value;
            return this;
        }

        public CopyObject trim(Consumer<String> consumer) {
            if (this.match) {
                consumer.accept(StringUtils.trim((String) this.value));
            }
            return this;
        }

        public <T> CopyObject set(Consumer<T> consumer) {
            if (this.match) {
                consumer.accept(CastUtils.cast(this.value));
            }
            return this;
        }

        public CopyObject set(BigDecimalConsumer consumer) {
            if (this.match) {
                consumer.accept((BigDecimal) this.value);
            }
            return this;
        }
    }

    @FunctionalInterface
    public interface BigDecimalConsumer extends Consumer<BigDecimal> {

    }
}
