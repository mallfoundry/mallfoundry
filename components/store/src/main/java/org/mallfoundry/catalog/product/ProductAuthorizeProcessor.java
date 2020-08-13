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

import org.mallfoundry.security.access.Resource;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 评估操作者对商品对象是否有操作权限。
 *
 * @author Zhi Tang
 */
public class ProductAuthorizeProcessor implements ProductProcessor {

    // hasPermission(storeId, 'storeType', 'product_add,product_manage')
    private static final String HAS_PERMISSION_ADD = "hasPermission(#product.storeId, '" + Resource.STORE_TYPE + "', '"
            + ProductAuthorities.PRODUCT_ADD + "," + ProductAuthorities.PRODUCT_MANAGE + "')";

    // hasPermission(storeId, 'storeType', 'product_update,product_manage')
    private static final String HAS_PERMISSION_UPDATE = "hasPermission(#product.storeId, '" + Resource.STORE_TYPE + "', '"
            + ProductAuthorities.PRODUCT_UPDATE + "," + ProductAuthorities.PRODUCT_MANAGE + "')";

    // hasPermission(storeId, 'storeType', 'product_delete,product_manage')
    private static final String HAS_PERMISSION_DELETE = "hasPermission(#product.storeId, '" + Resource.STORE_TYPE + "', '"
            + ProductAuthorities.PRODUCT_DELETE + "," + ProductAuthorities.PRODUCT_MANAGE + "')";

    // hasPermission(storeId, 'storeType', 'product_publish,product_manage')
    private static final String HAS_PERMISSION_PUBLISH = "hasPermission(#product.storeId, '" + Resource.STORE_TYPE + "', '"
            + ProductAuthorities.PRODUCT_PUBLISH + "," + ProductAuthorities.PRODUCT_MANAGE + "')";

    // hasPermission(storeId, 'storeType', 'product_unpublish,product_manage')
    private static final String HAS_PERMISSION_UNPUBLISH = "hasPermission(#product.storeId, '" + Resource.STORE_TYPE + "', '"
            + ProductAuthorities.PRODUCT_UNPUBLISH + "," + ProductAuthorities.PRODUCT_MANAGE + "')";

    @PreAuthorize(HAS_PERMISSION_ADD)
    @Override
    public Product preProcessBeforeAddProduct(Product product) {
        return product;
    }

    @PreAuthorize(HAS_PERMISSION_UPDATE)
    @Override
    public Product preProcessBeforeUpdateProduct(Product product) {
        return product;
    }

    @PreAuthorize(HAS_PERMISSION_PUBLISH)
    @Override
    public Product preProcessBeforePublishProduct(Product product) {
        return product;
    }

    @PreAuthorize(HAS_PERMISSION_UNPUBLISH)
    @Override
    public Product preProcessBeforeUnpublishProduct(Product product) {
        return product;
    }

    @PreAuthorize(HAS_PERMISSION_DELETE)
    @Override
    public Product preProcessBeforeDeleteProduct(Product product) {
        return product;
    }
}
