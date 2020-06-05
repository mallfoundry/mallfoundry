package org.mallfoundry.catalog.product;

public class InternalProductAddedEvent extends ProductEventSupport implements ProductAddedEvent {
    public InternalProductAddedEvent(Product product) {
        super(product);
    }
}
