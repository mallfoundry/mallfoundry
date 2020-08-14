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

package org.mallfoundry.catalog.product;

import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.inventory.InventoryAdjustment;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.util.Copies;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class DefaultProductService implements ProductService, ProductProcessorInvoker, ApplicationEventPublisherAware {

    private List<ProductProcessor> processors;

    private final ProductRepository productRepository;

    private ApplicationEventPublisher eventPublisher;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void setProcessors(List<ProductProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public ProductQuery createProductQuery() {
        return new DefaultProductQuery();
    }

    @Override
    public Product createProduct(String id) {
        return this.productRepository.create(id);
    }

    @Transactional
    @Override
    public Product addProduct(Product product) {
        product = this.invokePreProcessBeforeAddProduct(product);
        product.create();
        product = this.invokePreProcessAfterAddProduct(product);
        product = this.productRepository.save(product);
        this.eventPublisher.publishEvent(new ImmutableProductAddedEvent(product));
        return product;
    }

    @Transactional(readOnly = true)
    @Override
    public Product getProduct(String id) {
        return this.findProduct(id).orElseThrow();
    }

    public Product requiredProduct(String id) {
        return this.productRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProduct(String id) {
        return this.productRepository.findById(id)
                .map(this::invokePostProcessAfterGetProduct);
    }

    @Override
    public List<Product> getProducts(Collection<String> ids) {
        return this.productRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    @Override
    public SliceList<Product> getProducts(ProductQuery query) {
        return this.productRepository.findAll(
                this.invokePreProcessBeforeGetProducts(query));
    }

    private void updateProduct(Product source, Product dest) {
        Copies.notBlank(source::getName).trim(dest::setName)
                .notBlank(source::getBrandId).trim(dest::setBrandId)
                .notBlank(source::getDescription).trim(dest::setDescription)
                .notBlank(source::getCategoryId).trim(dest::setCategoryId)
                .notBlank(source::getShippingRateId).trim(dest::setShippingRateId);
        if (Objects.nonNull(source.getImageUrls())) {
            dest.setImageUrls(source.getImageUrls());
        }

        if (Objects.nonNull(source.getCollections())) {
            dest.setCollections(source.getCollections());
        }

        if (Objects.nonNull(source.getOrigin())) {
            dest.setOrigin(source.getOrigin());
        }

        if (Objects.nonNull(source.getFixedShippingCost())) {
            dest.setFixedShippingCost(source.getFixedShippingCost());
        }

        if (Objects.nonNull(source.getOptions())) {
            dest.updateOptions(source.getOptions());
        }

        if (Objects.nonNull(source.getVariants())) {
            dest.clearVariants();
            dest.addVariants(source.getVariants());
        }

        if (Objects.nonNull(source.getAttributes())) {
            dest.clearAttributes();
            dest.addAttributes(source.getAttributes());
        }
    }

    @Transactional
    @Override
    public Product updateProduct(Product source) {
        var product = this.requiredProduct(source.getId());
        try {
            product = this.invokePreProcessBeforeUpdateProduct(product);
            this.updateProduct(source, product);
            product = this.invokePreProcessAfterUpdateProduct(product);
        } finally {
            this.invokePreProcessAfterCompletion();
        }
        product = this.productRepository.save(product);
        this.eventPublisher.publishEvent(new ImmutableProductChangedEvent(product));
        return product;
    }

    @Transactional
    @Override
    public void publishProduct(String id) {
        var product = this.invokePreProcessBeforePublishProduct(this.requiredProduct(id));
        product.publish();
        var savedProduct = this.productRepository.save(product);
        this.eventPublisher.publishEvent(new ImmutableProductPublishedEvent(savedProduct));
    }

    @Transactional
    @Override
    public void publishProducts(Set<String> ids) {
        var products = this.invokePreProcessBeforePublishProducts(this.productRepository.findAllById(ids));
        products.forEach(Product::publish);
        var savedProducts = this.productRepository.saveAll(products);
        ListUtils.emptyIfNull(savedProducts)
                .forEach(product -> this.eventPublisher.publishEvent(new ImmutableProductPublishedEvent(product)));
    }

    @Transactional
    @Override
    public void unpublishProduct(String id) {
        var product = this.invokePreProcessBeforeUnpublishProduct(this.requiredProduct(id));
        product.unpublish();
        var savedProduct = this.productRepository.save(product);
        this.eventPublisher.publishEvent(new ImmutableProductArchivedEvent(savedProduct));
    }

    @Transactional
    @Override
    public void unpublishProducts(Set<String> ids) {
        var products = this.invokePreProcessBeforeUnpublishProducts(this.productRepository.findAllById(ids));
        products.forEach(Product::unpublish);
        var savedProducts = this.productRepository.saveAll(products);
        ListUtils.emptyIfNull(savedProducts)
                .forEach(product -> this.eventPublisher.publishEvent(new ImmutableProductArchivedEvent(product)));
    }

    @Transactional
    @Override
    public void adjustProductInventory(InventoryAdjustment adjustment) {
        // TODO 与订单模块自动化集成存在问题。将在引入系统用户后解决这个问题。
        /*var product = this.invokeProcessProduct(this.requireProduct(adjustment.getProductId()),
                ProductProcessorsInvoker::invokeProcessAdjustProductInventory);*/
        var product = this.requiredProduct(adjustment.getProductId());
        product.adjustInventoryQuantity(adjustment.getVariantId(), adjustment.getQuantityDelta());
    }

    @Transactional
    @Override
    public void adjustProductInventories(List<InventoryAdjustment> adjustments) {
        adjustments.forEach(this::adjustProductInventory);
    }

    @Transactional
    @Override
    public void deleteProduct(String id) {
        var product = this.invokePreProcessBeforeDeleteProduct(this.requiredProduct(id));
        // ....
        product = this.invokePreProcessAfterDeleteProduct(product);
        this.productRepository.delete(product);
        this.eventPublisher.publishEvent(new ImmutableProductDeletedEvent(product));
    }

    @Override
    public Product invokePostProcessAfterGetProduct(Product product) {
        return Processors.stream(this.processors)
                .map(ProductProcessor::postProcessAfterGetProduct)
                .apply(product);
    }

    @Override
    public ProductQuery invokePreProcessBeforeGetProducts(ProductQuery query) {
        return Processors.stream(this.processors)
                .map(ProductProcessor::preProcessBeforeGetProducts)
                .apply(query);
    }

    @Override
    public Product invokePreProcessBeforeAddProduct(Product product) {
        return Processors.stream(this.processors)
                .map(ProductProcessor::preProcessBeforeAddProduct)
                .apply(product);
    }

    @Override
    public Product invokePreProcessAfterAddProduct(Product product) {
        return Processors.stream(this.processors)
                .map(ProductProcessor::preProcessAfterAddProduct)
                .apply(product);
    }

    @Override
    public Product invokePreProcessBeforeUpdateProduct(Product product) {
        return Processors.stream(this.processors)
                .map(ProductProcessor::preProcessBeforeUpdateProduct)
                .apply(product);
    }

    @Override
    public Product invokePreProcessAfterUpdateProduct(Product product) {
        return Processors.stream(this.processors)
                .map(ProductProcessor::preProcessAfterUpdateProduct)
                .apply(product);
    }

    @Override
    public Product invokePreProcessBeforePublishProduct(Product product) {
        return Processors.stream(this.processors)
                .map(ProductProcessor::preProcessBeforePublishProduct)
                .apply(product);
    }

    @Override
    public List<Product> invokePreProcessBeforePublishProducts(List<Product> products) {
        return Processors.stream(this.processors)
                .map(ProductProcessor::preProcessBeforePublishProducts)
                .apply(products);
    }

    @Override
    public Product invokePreProcessBeforeUnpublishProduct(Product product) {
        return Processors.stream(this.processors)
                .map(ProductProcessor::preProcessBeforeUnpublishProduct)
                .apply(product);
    }

    @Override
    public List<Product> invokePreProcessBeforeUnpublishProducts(List<Product> products) {
        return Processors.stream(this.processors)
                .map(ProductProcessor::preProcessBeforeUnpublishProducts)
                .apply(products);
    }

    @Override
    public Product invokePreProcessBeforeDeleteProduct(Product product) {
        return Processors.stream(this.processors)
                .map(ProductProcessor::preProcessBeforeDeleteProduct)
                .apply(product);
    }

    @Override
    public Product invokePreProcessAfterDeleteProduct(Product product) {
        return Processors.stream(this.processors)
                .map(ProductProcessor::preProcessAfterDeleteProduct)
                .apply(product);
    }

    @Override
    public void invokePreProcessAfterCompletion() {
        this.processors.forEach(ProductProcessor::preProcessAfterCompletion);
    }
}
