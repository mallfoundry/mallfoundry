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

package org.mallfoundry.catalog.collection.repository;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.catalog.collection.ProductCollectionService;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductProcessor;
import org.springframework.core.NamedThreadLocal;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class ProductCollectionProductsCountProcessor implements ProductProcessor {

    private final ThreadLocal<Set<String>> localCollections = new NamedThreadLocal<>("Product Collection Ids");

    private final ProductCollectionService collectionService;

    public ProductCollectionProductsCountProcessor(ProductCollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Override
    public Product preProcessBeforeAddProduct(Product product) {
        var ids = product.getCollections();
        var collections = this.collectionService.getCollections(ids);
        collections.forEach(collection -> collection.addProduct(product));
        this.collectionService.updateCollections(collections);
        return product;
    }

    @Override
    public Product preProcessBeforeUpdateProduct(Product product) {
        localCollections.set(Collections.unmodifiableSet(product.getCollections()));
        return product;
    }

    @Override
    public Product preProcessAfterUpdateProduct(Product product) {
        var removedCollections = CollectionUtils.subtract(localCollections.get(), product.getCollections());
        var newCollections = CollectionUtils.subtract(product.getCollections(), localCollections.get());
        this.addProductToCollections(product, newCollections);
        this.removeProductFromCollections(product, removedCollections);
        return product;
    }

    @Override
    public void preProcessAfterCompletion() {
        this.localCollections.remove();
    }

    @Override
    public Product preProcessAfterDeleteProduct(Product product) {
        this.removeProductFromCollections(product, product.getCollections());
        return product;
    }

    private void addProductToCollections(Product product, Collection<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        var collections = this.collectionService.getCollections(ids);
        for (var collection : collections) {
            collection.addProduct(product);
        }
        this.collectionService.updateCollections(collections);
    }

    private void removeProductFromCollections(Product product, Collection<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        var collections = this.collectionService.getCollections(ids);
        for (var collection : collections) {
            collection.removeProduct(product);
        }
        this.collectionService.updateCollections(collections);
    }
}
