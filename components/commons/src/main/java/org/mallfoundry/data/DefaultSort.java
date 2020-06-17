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
