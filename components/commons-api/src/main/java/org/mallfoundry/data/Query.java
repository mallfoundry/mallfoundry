package org.mallfoundry.data;

public interface Query extends Pageable {

    void setSort(Sort sort);

    Sort getSort();
}
