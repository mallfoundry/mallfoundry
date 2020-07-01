package org.mallfoundry.data;

import java.util.function.Function;

public interface QueryBuilder<Q extends Query, B extends QueryBuilder<Q, B>> extends PageableBuilder<Q, B> {

    B sort(Function<Sort, Sort> function);
}
