/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.analytics.stream.product;

import org.mallfoundry.dw.ProductFact;
import org.mallfoundry.dw.ProductStatusDimension;
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
//        fact.setCategoryId(product.getCategoryId());
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
