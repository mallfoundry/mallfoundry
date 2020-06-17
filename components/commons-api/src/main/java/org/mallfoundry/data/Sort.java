package org.mallfoundry.data;

import java.io.Serializable;
import java.util.List;

public interface Sort {

    Sort from(String sort);

    Sort asc(String property);

    Sort desc(String property);

    List<Order> getOrders();

    enum Direction {
        ASC, DESC;

        public boolean isAscending() {
            return this.equals(ASC);
        }

        public boolean isDescending() {
            return this.equals(DESC);
        }
    }

    interface Order extends Serializable {

        String getProperty();

        void setProperty(String property);

        Direction getDirection();
    }

}
