package com.mallfoundry.data;

import com.mallfoundry.util.ObjectBuilder;

public interface PageableBuilder<O extends Pageable, B extends PageableBuilder<O, B>> extends ObjectBuilder<O> {

    B page(Integer page);

    B limit(Integer limit);
}
