package org.mallfoundry.catalog;

public class InternalBrandId implements BrandId {

    private final String id;

    public InternalBrandId(String id) {
        this.id = id;
    }

    @Override
    public String getIdentifier() {
        return this.id;
    }
}
