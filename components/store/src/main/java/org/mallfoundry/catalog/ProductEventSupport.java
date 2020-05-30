package org.mallfoundry.catalog;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public abstract class ProductEventSupport extends ApplicationEvent implements ProductEvent {

    @Getter
    private final Product product;

    public ProductEventSupport(Product product) {
        super(product);
        this.product = product;
    }
}
