package org.mallfoundry.catalog;

public class InternalCategoryId implements CategoryId {

    private final String id;

    public InternalCategoryId(String id) {
        this.id = id;
    }

    @Override
    public String getIdentifier() {
        return id;
    }
}
