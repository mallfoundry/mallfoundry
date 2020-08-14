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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.inventory.InventoryAdjustment;
import org.mallfoundry.processor.Processors;
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

    @Transactional
    @Override
    public Product updateProduct(Product product) {
        var oldProduct = this.invokePreProcessBeforeUpdateProduct(this.requiredProduct(product.getId()));

        if (Objects.nonNull(product.getName())
                && ObjectUtils.notEqual(product.getName(), oldProduct.getName())) {
            oldProduct.setName(product.getName());
        }

        if (Objects.nonNull(product.getImageUrls())
                && !ListUtils.isEqualList(product.getImageUrls(), oldProduct.getImageUrls())) {
            oldProduct.setImageUrls(product.getImageUrls());
        }

        if (Objects.nonNull(product.getBrandId())
                && ObjectUtils.notEqual(product.getBrandId(), oldProduct.getBrandId())) {
            oldProduct.setBrandId(product.getBrandId());
        }

        if (Objects.nonNull(product.getDescription())
                && ObjectUtils.notEqual(product.getDescription(), oldProduct.getDescription())) {
            oldProduct.setDescription(product.getDescription());
        }

        if (Objects.nonNull(product.getCollections())
                && !CollectionUtils.isEqualCollection(product.getCollections(), oldProduct.getCollections())) {
            oldProduct.setCollections(product.getCollections());
        }

        if (Objects.nonNull(product.getCategoryId())
                && ObjectUtils.notEqual(product.getCategoryId(), oldProduct.getCategoryId())) {
            oldProduct.setCategoryId(product.getCategoryId());
        }

        if (Objects.nonNull(product.getOrigin())
                && ObjectUtils.notEqual(product.getOrigin(), oldProduct.getOrigin())) {
            oldProduct.setOrigin(product.getOrigin());
        }

        if (Objects.nonNull(product.getFixedShippingCost())
                && ObjectUtils.notEqual(product.getFixedShippingCost(), oldProduct.getFixedShippingCost())) {
            oldProduct.setFixedShippingCost(product.getFixedShippingCost());
        }

        if (Objects.nonNull(product.getShippingRateId())
                && ObjectUtils.notEqual(product.getShippingRateId(), oldProduct.getShippingRateId())) {
            oldProduct.setShippingRateId(product.getShippingRateId());
        }

        if (product.isFreeShipping() && !oldProduct.isFreeShipping()) {
            oldProduct.freeShipping();
        }

        if (Objects.nonNull(product.getOptions())) {
            oldProduct.updateOptions(product.getOptions());
        }

        if (Objects.nonNull(product.getVariants())) {
            oldProduct.clearVariants();
            oldProduct.addVariants(product.getVariants());
        }

        if (Objects.nonNull(product.getAttributes())) {
            oldProduct.clearAttributes();
            oldProduct.addAttributes(product.getAttributes());
        }
        var savedProduct = this.productRepository.save(this.invokePreProcessAfterUpdateProduct(oldProduct));
        this.eventPublisher.publishEvent(new ImmutableProductChangedEvent(savedProduct));
        return savedProduct;
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
}
