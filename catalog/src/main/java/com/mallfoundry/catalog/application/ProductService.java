package com.mallfoundry.catalog.application;

import com.mallfoundry.catalog.domain.product.ProductQuery;
import com.mallfoundry.catalog.domain.product.ProductRepository;
import com.mallfoundry.storefront.domain.product.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("catalogProductService")
public class ProductService {

    private final ProductRepository productSearchRepository;

    public ProductService(ProductRepository productSearchRepository) {
        this.productSearchRepository = productSearchRepository;
    }

    public List<Product> search(ProductQuery query) {
        return this.productSearchRepository.search(query);
    }

    public Product getProduct(String id) {
        return this.productSearchRepository.getProduct(id);
    }

    public List<Product> getProducts(List<String> ids) {
        return this.productSearchRepository.getProducts(ids);
    }
}
