package org.mallfoundry.catalog.product;

public class ImmutableProductAddedEvent extends ProductEventSupport implements ProductAddedEvent {
    public ImmutableProductAddedEvent(Product product) {
        super(product);
    }
}
