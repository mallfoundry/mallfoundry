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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class DefaultProductService implements ProductService {

    private final ProductProcessorsInvoker processorsInvoker;

    private final ProductRepository primaryRepository;

    private final SearchProductRepository searchRepository;

    private final ApplicationEventPublisher eventPublisher;

    public DefaultProductService(ProductProcessorsInvoker processorsInvoker,
                                 ProductRepository primaryRepository,
                                 SearchProductRepository searchRepository,
                                 ApplicationEventPublisher eventPublisher) {
        this.processorsInvoker = processorsInvoker;
        this.primaryRepository = primaryRepository;
        this.searchRepository = searchRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public ProductQuery createProductQuery() {
        return new DefaultProductQuery();
    }

    @Override
    public ProductId createProductId(String id) {
        return new ImmutableProductId(id);
    }

    @Override
    public Product createProduct(String id) {
        return this.primaryRepository.create(id);
    }

    @Transactional
    @Override
    public Product addProduct(Product product) {
        var addedProduct = this.primaryRepository.save(
                this.processorsInvoker.invokePreProcessAddProduct(product));
        this.eventPublisher.publishEvent(new ImmutableProductAddedEvent(addedProduct));
        return addedProduct;
    }

    @Transactional(readOnly = true)
    public Optional<Product> getProduct(String id) {
        return this.primaryRepository.findById(id)
                .map(this.processorsInvoker::invokePostProcessGetProduct);
    }

    @Transactional(readOnly = true)
    @Override
    public SliceList<Product> getProducts(ProductQuery query) {
        return this.searchRepository.findAll(
                this.processorsInvoker.invokePreProcessGetProducts(query));
    }

    private Product requireProduct(String id) {
        return this.primaryRepository.findById(id).orElseThrow();
    }

    private Product invokeProcessProduct(Product product, BiFunction<ProductProcessorsInvoker, Product, Product> function) {
        return function.apply(this.processorsInvoker, product);
    }

    @Transactional
    @Override
    public Product updateProduct(Product product) {
        var oldProduct = this.invokeProcessProduct(this.requireProduct(product.getId()),
                ProductProcessorsInvoker::invokePreProcessUpdateProduct);

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

        if (Objects.nonNull(product.getShippingOrigin())
                && ObjectUtils.notEqual(product.getShippingOrigin(), oldProduct.getShippingOrigin())) {
            oldProduct.setShippingOrigin(product.getShippingOrigin());
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
            oldProduct.clearOptions();
            oldProduct.addOptions(product.getOptions());
        }

        if (Objects.nonNull(product.getVariants())) {
            oldProduct.clearVariants();
            oldProduct.addVariants(product.getVariants());
        }

        if (Objects.nonNull(product.getAttributes())) {
            oldProduct.clearAttributes();
            oldProduct.addAttributes(product.getAttributes());
        }
        var savedProduct =
                this.primaryRepository.save(
                        this.invokeProcessProduct(oldProduct,
                                ProductProcessorsInvoker::invokePostProcessUpdateProduct));
        this.eventPublisher.publishEvent(new ImmutableProductChangedEvent(savedProduct));
        return savedProduct;
    }

    @Transactional
    @Override
    public void publishProduct(String id) {
        var product = this.invokeProcessProduct(this.requireProduct(id),
                ProductProcessorsInvoker::invokePreProcessPublishProduct);
        product.publish();
        var savedProduct = this.primaryRepository.save(product);
        this.eventPublisher.publishEvent(new ImmutableProductPublishedEvent(savedProduct));
    }

    @Transactional
    @Override
    public void publishProducts(Set<String> ids) {
        var products = this.primaryRepository.findAllById(ids).stream()
                .map(processorsInvoker::invokePreProcessPublishProduct)
                .peek(Product::publish)
                .collect(Collectors.toList());
        var savedProducts = this.primaryRepository.saveAll(products);
        ListUtils.emptyIfNull(savedProducts)
                .forEach(product -> this.eventPublisher.publishEvent(new ImmutableProductPublishedEvent(product)));
    }

    @Transactional
    @Override
    public void unpublishProduct(String id) {
        var product = this.invokeProcessProduct(this.requireProduct(id),
                ProductProcessorsInvoker::invokePreProcessUnpublishProduct);
        product.unpublish();
        var savedProduct = this.primaryRepository.save(product);
        this.eventPublisher.publishEvent(new ImmutableProductArchivedEvent(savedProduct));
    }

    @Transactional
    @Override
    public void unpublishProducts(Set<String> ids) {
        var products = this.primaryRepository.findAllById(ids).stream()
                .map(processorsInvoker::invokePreProcessUnpublishProduct)
                .peek(Product::unpublish)
                .collect(Collectors.toList());
        var savedProducts = this.primaryRepository.saveAll(products);
        ListUtils.emptyIfNull(savedProducts)
                .forEach(product -> this.eventPublisher.publishEvent(new ImmutableProductArchivedEvent(product)));
    }

    @Transactional
    @Override
    public void adjustProductInventory(InventoryAdjustment adjustment) {
        // TODO 与订单模块自动化集成存在问题。将在引入系统用户后解决这个问题。
        /*var product = this.invokeProcessProduct(this.requireProduct(adjustment.getProductId()),
                ProductProcessorsInvoker::invokeProcessAdjustProductInventory);*/
        var product = this.requireProduct(adjustment.getProductId());
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
        var product = this.invokeProcessProduct(this.requireProduct(id),
                ProductProcessorsInvoker::invokePreProcessDeleteProduct);
        this.primaryRepository.delete(product);
        this.eventPublisher.publishEvent(new ImmutableProductDeletedEvent(product));
    }
}
