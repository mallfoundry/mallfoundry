package org.mallfoundry.data;

import org.springframework.util.Assert;

import java.util.Objects;

public abstract class QuerySupport extends PageableSupport implements Query {

    protected Sort sort;

    @Override
    public void setSort(Sort sort) {
        Assert.notNull(sort, "Sort must not be null");
        this.sort = sort;
    }

    @Override
    public Sort getSort() {
        if (Objects.isNull(this.sort)) {
            sort = new DefaultSort();
        }
        return this.sort;
    }
}
