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

package org.mallfoundry.processor;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public abstract class ProcessorsInvoker {

    @SuppressWarnings("unchecked")
    public static <T, U, R extends U> R invokeProcessors(List<T> processors, U product, BiFunction<T, U, R> function) {
        U result = product;
        for (var processor : processors) {
            result = function.apply(processor, result);
        }
        return (R) result;
    }

    public static <T> void invokeProcessors(List<T> processors, Consumer<T> consumer) {
        for (var processor : processors) {
            consumer.accept(processor);
        }
    }
}
