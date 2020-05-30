package org.mallfoundry.catalog;

public class InternalProductAddedEvent extends ProductEventSupport {
    public InternalProductAddedEvent(Product product) {
        super(product);
    }
}
