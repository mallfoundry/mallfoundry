package org.mallfoundry.catalog.product;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductProcessorsInvoker {

    private final List<ProductProcessor> processors;

    public ProductProcessorsInvoker(List<ProductProcessor> processors) {
        this.processors = processors;
    }

    public Product invokeProcessPreAddProduct(Product product) {
        var result = product;
        for (var processor : processors) {
            result = processor.processPreAddProduct(result);
        }
        return result;
    }
}
