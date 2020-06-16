package org.mallfoundry.data;

import org.springframework.util.Assert;

public class QuerySupport extends PageableSupport implements Query {

    protected Sort sort = new DefaultSort();

    @Override
    public void setSort(Sort sort) {
        Assert.notNull(sort, "The sort property must not be null");
        this.sort = sort;
    }

    @Override
    public Sort getSort() {
        return this.sort;
    }
}
