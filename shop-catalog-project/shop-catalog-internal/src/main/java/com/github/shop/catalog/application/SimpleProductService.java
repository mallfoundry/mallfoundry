/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.shop.catalog.application;

import com.github.shop.catalog.CreatedProductEvent;
import com.github.shop.catalog.Product;
import com.github.shop.catalog.ProductCategory;
import com.github.shop.catalog.ProductCategoryRepository;
import com.github.shop.catalog.ProductRepository;
import com.github.shop.catalog.ProductService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SimpleProductService implements ProductService, ApplicationEventPublisherAware {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository categoryRepository;

    private ApplicationEventPublisher eventPublisher;

    public SimpleProductService(ProductRepository productRepository, ProductCategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public void createProductCategory(ProductCategory category) {
        this.categoryRepository.create(category);
    }

    @Transactional
    @Override
    public void createProductCategories(List<ProductCategory> categories) {
        this.categoryRepository.create(categories);
    }

    @Transactional
    @Override
    public void deleteProductCategory(long id) {
        this.categoryRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateProductCategory(ProductCategory category) {
        this.categoryRepository.update(category);
    }

    @Override
    public List<ProductCategory> getTopProductCategories() {
        return this.categoryRepository.findTopList();
    }

    @Override
    public List<ProductCategory> getProductCategories(long parentId) {
        return this.categoryRepository.findListByParentId(parentId);
    }

    @Override
    public ProductCategory getProductCategory(long id) {
        return this.categoryRepository.findById(id);
    }

    @Transactional
    @Override
    public void createProduct(Product product) {
        this.productRepository.create(product);
        // Publish create product event.
        this.eventPublisher.publishEvent(new CreatedProductEvent(product.getId()));
    }

    @Override
    public Product getProduct(long id) {
        return this.productRepository.findById(id);
    }
}
