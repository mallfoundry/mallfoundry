package org.mallfoundry.data;

import lombok.Getter;

@Getter
public class DefaultOrder implements SortOrder {

    private final String property;

    private final Direction direction;

    public DefaultOrder(String property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return String.format("%s %s", property, direction);
    }
}
