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

package org.mallfoundry.data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultSort implements Sort {

    private final List<Order> orders = new ArrayList<>();

    @Override
    public Sort from(String string) {
        Stream.ofNullable(string)
                .flatMap(str -> Arrays.stream(str.split(",")))
                .map(str -> {
                    if (str.contains(":")) {
                        var propertyAndDirection = str.split(":");
                        var direction = Direction.valueOf(propertyAndDirection[1].toUpperCase());
                        return new DefaultOrder(propertyAndDirection[0], direction);
                    } else {
                        return new DefaultOrder(str, Direction.ASC);
                    }
                })
                .forEach(this.orders::add);
        return this;
    }

    @Override
    public Sort asc(String property) {
        this.orders.add(new DefaultOrder(property, Direction.ASC));
        return this;
    }

    @Override
    public Sort desc(String property) {
        this.orders.add(new DefaultOrder(property, Direction.DESC));
        return this;
    }

    @Override
    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    @Override
    public String toString() {
        return this.orders.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    @Getter
    @Setter
    private static class DefaultOrder implements Sort.Order {

        private String property;

        private final Direction direction;

        DefaultOrder(String property, Direction direction) {
            this.property = property;
            this.direction = direction;
        }

        @Override
        public String toString() {
            return String.format("%s %s", property, direction);
        }
    }

}
