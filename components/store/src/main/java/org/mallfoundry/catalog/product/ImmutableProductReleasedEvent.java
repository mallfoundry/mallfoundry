package org.mallfoundry.catalog.product;

public class ImmutableProductReleasedEvent extends ProductEventSupport implements ProductEvent {
    public ImmutableProductReleasedEvent(Product product) {
        super(product);
    }
}
