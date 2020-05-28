package org.mallfoundry.data;

import org.mallfoundry.util.ObjectBuilder;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface PageableBuilder<O extends Pageable, B extends PageableBuilder<O, B>> extends ObjectBuilder<O> {

    B page(Integer page);

    B limit(Integer limit);
}
