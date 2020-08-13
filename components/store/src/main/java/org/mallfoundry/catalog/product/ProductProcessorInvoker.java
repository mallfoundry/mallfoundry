/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General  License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General  License for more details.
 *
 * You should have received a copy of the GNU General  License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.catalog.product;

import java.util.List;

interface ProductProcessorInvoker {

    Product invokePostProcessAfterGetProduct(Product product);

    ProductQuery invokePreProcessBeforeGetProducts(ProductQuery query);

    Product invokePreProcessBeforeAddProduct(Product product);

    Product invokePreProcessAfterAddProduct(Product product);

    Product invokePreProcessBeforeUpdateProduct(Product product);

    Product invokePreProcessAfterUpdateProduct(Product product);

    Product invokePreProcessBeforePublishProduct(Product product);

    List<Product> invokePreProcessBeforePublishProducts(List<Product> products);

    Product invokePreProcessBeforeUnpublishProduct(Product product);

    List<Product> invokePreProcessBeforeUnpublishProducts(List<Product> products);

    Product invokePreProcessBeforeDeleteProduct(Product product);
}
