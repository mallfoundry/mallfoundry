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

    // Product collection Authorities
    public static final String PRODUCT_COLLECTION_READ = "product_collection:read";
    public static final String PRODUCT_COLLECTION_WRITE = "product_collection:write";
    public static final String PRODUCT_COLLECTION_DELETE = "product_collection:delete";
    public static final String PRODUCT_COLLECTION_MANAGE = "product_collection:manage";

    // Product Authorities
    public static final String PRODUCT_READ = "product:read";
    public static final String PRODUCT_WRITE = "product:write";
    public static final String PRODUCT_DELETE = "product:delete";
    public static final String PRODUCT_MANAGE = "product:manage";

    // Order Authorities
    public static final String ORDER_READ = "order:read";
    public static final String ORDER_WRITE = "order:write";
    public static final String ORDER_DELETE = "order:delete";
    public static final String ORDER_MANAGE = "order:manage";
}
