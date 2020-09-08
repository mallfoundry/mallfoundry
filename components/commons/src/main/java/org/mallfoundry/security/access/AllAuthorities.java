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

package org.mallfoundry.security.access;

public abstract class AllAuthorities extends StoreAuthorities {

    public static final String SUPER_ADMIN = "super_admin";
    public static final String ADMIN = "admin";

    // Product collection Authorities
    public static final String PRODUCT_COLLECTION_ADD = "product_collection_add";
    public static final String PRODUCT_COLLECTION_READ = "product_collection_read";
    public static final String PRODUCT_COLLECTION_WRITE = "product_collection_write";
    public static final String PRODUCT_COLLECTION_DELETE = "product_collection_delete";
    public static final String PRODUCT_COLLECTION_MANAGE = "product_collection_manage";

    // Product Authorities
    public static final String PRODUCT_ADD = "product_add";
    public static final String PRODUCT_READ = "product_read";
    public static final String PRODUCT_WRITE = "product_write";
    public static final String PRODUCT_PUBLISH = "product_publish";
    public static final String PRODUCT_UNPUBLISH = "product_unpublish";
    public static final String PRODUCT_DELETE = "product_delete";
    public static final String PRODUCT_MANAGE = "product_manage";

    // Order Authorities
    public static final String ORDER_READ = "order_read";
    public static final String ORDER_WRITE = "order_write";
    public static final String ORDER_DELETE = "order_delete";
    public static final String ORDER_FULFIL = "order_fulfil";
    public static final String ORDER_SIGN = "order_sign";
    public static final String ORDER_RECEIPT = "order_receipt";
    public static final String ORDER_CANCEL = "order_cancel";
    public static final String ORDER_MANAGE = "order_manage";

    public static final String ORDER_SHIPMENT_ADD = "order_shipment_add";
    public static final String ORDER_SHIPMENT_READ = "order_shipment_read";
    public static final String ORDER_SHIPMENT_WRITE = "order_shipment_write";
    public static final String ORDER_SHIPMENT_REMOVE = "order_shipment_remove";
    public static final String ORDER_SHIPMENT_MANAGE = "order_shipment_manage";

    public static final String ORDER_REFUND_READ = "order_refund_read";
    public static final String ORDER_REFUND_APPLY = "order_refund_apply";
    public static final String ORDER_REFUND_CANCEL = "order_refund_cancel";
    public static final String ORDER_REFUND_APPROVE = "order_refund_approve";
    public static final String ORDER_REFUND_DISAPPROVE = "order_refund_disapprove";
    public static final String ORDER_REFUND_ACTIVE = "order_refund_active";
    public static final String ORDER_REFUND_MANAGE = "order_refund_manage";

    public static final String ORDER_REVIEW = "order_review";

    // Customer Authorities
    public static final String CUSTOMER_MANAGE = "customer_manage";

    // Storage bucket Authorities
    public static final String STORAGE_BUCKET_READ = "storage_bucket_read";
    public static final String STORAGE_BUCKET_WRITE = "storage_bucket_write";
    public static final String STORAGE_BUCKET_DELETE = "storage_bucket_delete";
    public static final String STORAGE_BUCKET_MANAGE = "storage_bucket_manage";
}
