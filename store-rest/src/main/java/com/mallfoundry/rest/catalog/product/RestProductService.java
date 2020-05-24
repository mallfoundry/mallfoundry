package com.mallfoundry.rest.catalog.product;

import com.mallfoundry.catalog.product.Product;
import com.mallfoundry.catalog.product.ProductService;
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
