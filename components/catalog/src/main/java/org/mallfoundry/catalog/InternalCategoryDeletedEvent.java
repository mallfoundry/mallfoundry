package org.mallfoundry.catalog;

public class InternalCategoryDeletedEvent extends CategoryEventSupport {
    public InternalCategoryDeletedEvent(Category category) {
        super(category);
    }
}
