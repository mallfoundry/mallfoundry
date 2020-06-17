package org.mallfoundry.catalog.product;

import org.mallfoundry.data.SliceList;

import java.util.Optional;

public class ProductRepositoryDelegate implements ProductRepository {

    private final JdbcProductRepository jdbcProductRepository;

    private final SearchProductRepository searchProductRepository;

    public ProductRepositoryDelegate(JdbcProductRepository jdbcProductRepository,
                                     SearchProductRepository searchProductRepository) {
        this.jdbcProductRepository = jdbcProductRepository;
        this.searchProductRepository = searchProductRepository;
    }

    @Override
    public Product create(String id) {
        return this.jdbcProductRepository.create(id);
    }

    @Override
    public Product save(Product product) {
        this.searchProductRepository.save(product);
        return this.jdbcProductRepository.save(product);
    }

    @Override
    public Optional<Product> findById(String id) {
        return this.searchProductRepository.findById(id);
    }

    @Override
    public SliceList<Product> findAll(ProductQuery query) {
        return this.searchProductRepository.findAll(query);
    }
}
