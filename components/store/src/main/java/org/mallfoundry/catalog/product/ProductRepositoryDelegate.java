package org.mallfoundry.catalog.product;

import java.util.List;
import java.util.Optional;

public class ProductRepositoryDelegate implements ProductRepository {

    private final List<ProductRepository> repositories;

    public ProductRepositoryDelegate(List<ProductRepository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public Product create(String id) {
        return null;
    }

    @Override
    public Product save(Product product) {
//...
//        repositories.forEach();
        return null;
    }

    @Override
    public Optional<Product> findById(String id) {

        // repositories.findBy
//        repositories.sele

        return Optional.empty();
    }
}
