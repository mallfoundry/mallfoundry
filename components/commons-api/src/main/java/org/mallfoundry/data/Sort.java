package org.mallfoundry.data;

import java.util.List;

public interface Sort {

    Sort from(String sort);

    Sort asc(String property);

    Sort desc(String property);

    List<SortOrder> getOrders();
}
