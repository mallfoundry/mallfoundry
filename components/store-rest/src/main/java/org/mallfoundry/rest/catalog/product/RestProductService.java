package org.mallfoundry.rest.catalog.product;

import org.mallfoundry.catalog.Product;
import org.mallfoundry.catalog.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestProductService {

    private final ProductService productService;

    public RestProductService(ProductService productService) {
        this.productService = productService;
    }

    @Transactional
    public Product getProduct(String id) {
        return this.productService.getProduct(id).orElseThrow();
    }
}
