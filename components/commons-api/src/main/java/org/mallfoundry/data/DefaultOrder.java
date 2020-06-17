package org.mallfoundry.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultOrder implements SortOrder {

    private String property;

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
