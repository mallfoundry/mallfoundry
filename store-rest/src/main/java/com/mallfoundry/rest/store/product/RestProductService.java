package com.mallfoundry.rest.store.product;

import com.mallfoundry.store.product.Product;
import com.mallfoundry.store.product.ProductService;
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
