package org.mallfoundry.data;

import java.util.function.Function;

public abstract class QueryBuilderSupport<O extends Query, B extends QueryBuilder<O, B>>
        implements QueryBuilder<O, B> {

    private final O query;

    public QueryBuilderSupport(O query) {
        this.query = query;
    }

    @Override
    public QueryBuilder<O, B> sort(Function<O, Sort> function) {
        this.query.setSort(function.apply(this.query));
        return this;
    }
}

