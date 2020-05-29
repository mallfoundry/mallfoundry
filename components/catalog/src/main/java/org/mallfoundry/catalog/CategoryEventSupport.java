package org.mallfoundry.catalog;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public abstract class CategoryEventSupport extends ApplicationEvent implements CategoryEvent {

    @Getter
    private final Category category;

    public CategoryEventSupport(Category category) {
        super(category);
        this.category = category;
    }
}
