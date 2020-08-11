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

public abstract class StoreAuthorities {

    // Store Authorities
    public static final String STORE_READ = "store:read";
    public static final String STORE_WRITE = "store:write";
    public static final String STORE_MANAGE = "store:manage";

    // Store address Authorities
    public static final String STORE_ADDRESS_READ = "store:address:read";
    public static final String STORE_ADDRESS_WRITE = "store:address:write";
    public static final String STORE_ADDRESS_DELETE = "store:address:delete";
    public static final String STORE_ADDRESS_MANAGE = "store:address:manage";

    // Store product collection Authorities
    public static final String STORE_PRODUCT_COLLECTION_READ = "store:product_collection:read";
    public static final String STORE_PRODUCT_COLLECTION_WRITE = "store:product_collection:write";
    public static final String STORE_PRODUCT_COLLECTION_MANAGE = "store:product_collection:manage";

    // Store staff Authorities
    public static final String STORE_STAFF_READ = "store:staff:read";
    public static final String STORE_STAFF_WRITE = "store:staff:write";
    public static final String STORE_STAFF_DELETE = "store:staff:delete";
    public static final String STORE_STAFF_MANAGE = "store:staff:manage";

    // Store role Authorities
    public static final String STORE_ROLE_READ = "store:role:read";
    public static final String STORE_ROLE_WRITE = "store:role:write";
    public static final String STORE_ROLE_DELETE = "store:role:delete";
    public static final String STORE_ROLE_MANAGE = "store:role:manage";

    // Store member Authorities
    public static final String STORE_MEMBER_READ = "store:member:read";
    public static final String STORE_MEMBER_WRITE = "store:member:write";
    public static final String STORE_MEMBER_DELETE = "store:member:delete";
    public static final String STORE_MEMBER_MANAGE = "store:member:manage";
}
