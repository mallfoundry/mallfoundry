package org.mallfoundry.data;

import java.util.function.Function;

public interface QueryBuilder<O extends Query, B extends QueryBuilder<O, B>> extends PageableBuilder<O, B> {

    QueryBuilder<O, B> sort(Function<O, Sort> function);
}
