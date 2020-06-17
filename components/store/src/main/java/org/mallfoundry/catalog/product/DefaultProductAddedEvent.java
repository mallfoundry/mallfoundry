package org.mallfoundry.catalog.product;

public class DefaultProductAddedEvent extends ProductEventSupport implements ProductAddedEvent {
    public DefaultProductAddedEvent(Product product) {
        super(product);
    }
}
