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

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
public class ProductProcessorsInvoker {

    private final List<ProductProcessor> processors;

    public ProductProcessorsInvoker(List<ProductProcessor> processors) {
        this.processors = processors;
    }

    private Product invokeProcess(Product product, BiFunction<ProductProcessor, Product, Product> function) {
        var result = product;
        for (var processor : processors) {
            result = function.apply(processor, result);
        }
        return result;
    }

    private ProductQuery invokeProcess(ProductQuery query, BiFunction<ProductProcessor, ProductQuery, ProductQuery> function) {
        var result = query;
        for (var processor : processors) {
            result = function.apply(processor, result);
        }
        return result;
    }

    public Product invokePostProcessGetProduct(Product product) {
        return this.invokeProcess(product, ProductProcessor::postProcessGetProduct);
    }

    public ProductQuery invokePreProcessGetProducts(ProductQuery query) {
        return this.invokeProcess(query, ProductProcessor::preProcessGetProducts);
    }

    public Product invokePreProcessAddProduct(Product product) {
        return this.invokeProcess(product, ProductProcessor::preProcessAddProduct);
    }

    public Product invokePreProcessUpdateProduct(Product product) {
        return this.invokeProcess(product, ProductProcessor::preProcessUpdateProduct);
    }

    public Product invokePostProcessUpdateProduct(Product product) {
        return this.invokeProcess(product, ProductProcessor::postProcessUpdateProduct);
    }

    public Product invokePreProcessPublishProduct(Product product) {
        return this.invokeProcess(product, ProductProcessor::preProcessPublishProduct);
    }

    public Product invokePreProcessUnpublishProduct(Product product) {
        return this.invokeProcess(product, ProductProcessor::preProcessUnpublishProduct);
    }

/*    public Product invokePreProcessAdjustProductInventory(Product product) {
        return this.invokeProcess(product, ProductProcessor::preProcessAdjustProductInventory);
    }*/

    public Product invokePreProcessDeleteProduct(Product product) {
        return this.invokeProcess(product, ProductProcessor::preProcessDeleteProduct);
    }
}
