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

import java.util.List;

/**
 * 商品对象处理器，用于增强商品对象。
 *
 * @author Zhi Tang
 */
public interface ProductProcessor {

    default Product postProcessAfterGetProduct(Product product) {
        return product;
    }

    default ProductQuery preProcessBeforeGetProducts(ProductQuery query) {
        return query;
    }

    default Product preProcessBeforeAddProduct(Product product) {
        return product;
    }

    default Product preProcessAfterAddProduct(Product product) {
        return product;
    }

    default Product preProcessBeforeUpdateProduct(Product product) {
        return product;
    }

    default Product preProcessAfterUpdateProduct(Product product) {
        return product;
    }

    default Product preProcessBeforePublishProduct(Product product) {
        return product;
    }

    default List<Product> preProcessBeforePublishProducts(List<Product> products) {
        return products;
    }

    default Product preProcessBeforeUnpublishProduct(Product product) {
        return product;
    }

    default List<Product> preProcessBeforeUnpublishProducts(List<Product> products) {
        return products;
    }

    default Product preProcessBeforeDeleteProduct(Product product) {
        return product;
    }
}
