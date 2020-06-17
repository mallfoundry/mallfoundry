package org.mallfoundry.data;

import java.util.function.Function;

public abstract class QueryBuilderSupport<O extends Query, B extends QueryBuilder<O, B>>
        extends PageableBuilderSupport<O, B>
        implements QueryBuilder<O, B> {

    private final O query;

    public QueryBuilderSupport(O query) {
        super(query);
        this.query = query;
    }

    @SuppressWarnings("unchecked")
    @Override
    public B sort(Function<Sort, Sort> function) {
        this.query.setSort(function.apply(new DefaultSort()));
        return (B) this;
    }
}

