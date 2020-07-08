package org.mallfoundry.analytics.stream.product;

import org.mallfoundry.analytics.models.ProductFact;
import org.mallfoundry.analytics.models.ProductStatusDimension;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductAddedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class ProductEventStreams {

    private static final Logger logger = LoggerFactory.getLogger(ProductEventStreams.class);

    private final ProductFactRepository productFactRepository;
    private final ProductQuantityFactRepository quantityFactRepository;

    public ProductEventStreams(ProductFactRepository productFactRepository,
                               ProductQuantityFactRepository quantityFactRepository) {
        this.productFactRepository = productFactRepository;
        this.quantityFactRepository = quantityFactRepository;
    }

    private ProductFact createProductFact(Product product) {
        var fact = new ProductFact();
        fact.setId(product.getId());
        fact.setBrandId(product.getBrandId());
        fact.setCategoryId(product.getCategoryId());
        fact.setStatusId(ProductStatusDimension.idOf(product.getStatus()));
        fact.setStoreId(product.getStoreId());
        return fact;
    }

    @Transactional
    @EventListener
    public void handleProductAddedEvent(ProductAddedEvent event) {
        var fact = this.createProductFact(event.getProduct());
        this.productFactRepository.save(fact);
        var quantityFacts = this.productFactRepository.countAll(fact);
        this.quantityFactRepository.deleteByStoreId(fact.getStoreId());
        this.quantityFactRepository.saveAll(quantityFacts);
    }
}
