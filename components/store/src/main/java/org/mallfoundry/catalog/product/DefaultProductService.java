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

import org.mallfoundry.data.SliceList;
import org.mallfoundry.inventory.InventoryAdjustment;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class DefaultProductService implements ProductService {

    private final ProductProcessorsInvoker processorsInvoker;

    private final ProductRepository productRepository;

    private final ApplicationEventPublisher eventPublisher;

    public DefaultProductService(ProductProcessorsInvoker processorsInvoker,
                                 ProductRepository productRepository,
                                 ApplicationEventPublisher eventPublisher) {
        this.processorsInvoker = processorsInvoker;
        this.productRepository = productRepository;
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
        return this.productRepository.create(id);
    }

    @Transactional
    @Override
    public Product addProduct(Product product) {
        var addedProduct = this.productRepository.save(
                this.processorsInvoker.invokeProcessPreAddProduct(product));
        this.eventPublisher.publishEvent(new ImmutableProductAddedEvent(addedProduct));
        return addedProduct;
    }

    @Transactional(readOnly = true)
    public Optional<Product> getProduct(String id) {
        return this.productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public SliceList<Product> getProducts(ProductQuery query) {
        return this.productRepository.findAll(query);
    }

    @Transactional
    @Override
    public void updateProduct(Product product) {
        var oldProduct = this.productRepository.findById(product.getId()).orElseThrow();
        BeanUtils.copyProperties(product, oldProduct, "variants");
        var savedProduct = this.productRepository.save(oldProduct);
        this.eventPublisher.publishEvent(new ImmutableProductChangedEvent(savedProduct));
    }

    @Transactional
    @Override
    public void adjustInventory(InventoryAdjustment adjustment) {
        var product = this.productRepository.findById(adjustment.getProductId()).orElseThrow();
        product.adjustInventoryQuantity(adjustment.getVariantId(), adjustment.getQuantityDelta());
    }

    @Transactional
    @Override
    public void adjustInventories(List<InventoryAdjustment> adjustments) {
        adjustments.forEach(this::adjustInventory);
    }
}
