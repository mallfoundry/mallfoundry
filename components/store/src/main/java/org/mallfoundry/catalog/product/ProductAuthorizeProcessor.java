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

import org.mallfoundry.security.access.AllAuthorities;
import org.mallfoundry.security.access.Resource;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 评估操作者对商品对象是否有操作权限。
 *
 * @author Zhi Tang
 */
public class ProductAuthorizeProcessor implements ProductProcessor {

    @PreAuthorize("hasPermission(#product.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.PRODUCT_ADD + ","
            + AllAuthorities.PRODUCT_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Product preProcessBeforeAddProduct(Product product) {
        return product;
    }

    @PreAuthorize("hasPermission(#product.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.PRODUCT_WRITE + ","
            + AllAuthorities.PRODUCT_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Product preProcessBeforeUpdateProduct(Product product) {
        return product;
    }

    @PreAuthorize("hasPermission(#product.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.PRODUCT_PUBLISH + ","
            + AllAuthorities.PRODUCT_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Product preProcessBeforePublishProduct(Product product) {
        return product;
    }

    @PreAuthorize("hasPermission(#product.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.PRODUCT_UNPUBLISH + ","
            + AllAuthorities.PRODUCT_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Product preProcessBeforeUnpublishProduct(Product product) {
        return product;
    }

    @PreAuthorize("hasPermission(#product.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.PRODUCT_DELETE + ","
            + AllAuthorities.PRODUCT_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Product preProcessBeforeDeleteProduct(Product product) {
        return product;
    }
}
