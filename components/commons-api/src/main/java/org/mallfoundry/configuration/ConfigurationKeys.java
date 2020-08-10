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

package org.mallfoundry.configuration;

import java.util.Set;

public abstract class ConfigurationKeys {

    private static final String TENANT_KEY_PREFIX = "tenant.";

    //
    private static final String TENANT_STORES_KEY_PREFIX = TENANT_KEY_PREFIX + "stores.";

    public static final String TENANT_STORES_DEFAULT_EDITION_KEY = TENANT_STORES_KEY_PREFIX + "default-edition";


    // Store key prefix
    private static final String STORE_KEY_PREFIX = "store.";

    public static final String STORE_MEMBER_AUTO_JOIN_CONDITIONAL_KEY = STORE_KEY_PREFIX + "member.auto-join-conditional";

    // Store Property keys
    public static final String STORE_PRODUCTS_MAX_SIZE_KEY = STORE_KEY_PREFIX + "products.max-size";
    public static final String STORE_PRODUCT_COLLECTIONS_MAX_SIZE_KEY = STORE_KEY_PREFIX + "product-collections.max-size";
    public static final String STORE_STAFFS_MAX_SIZE_KEY = STORE_KEY_PREFIX + "staffs.max-size";
    public static final String STORE_ROLES_MAX_SIZE_KEY = STORE_KEY_PREFIX + "roles.max-size";
    public static final String STORE_ADDRESSES_MAX_SIZE_KEY = STORE_KEY_PREFIX + "addresses.max-size";

    // Store edition scope key set
    public static final Set<String> STORE_EDITION_SCOPE_KEY_SET =
            Set.of(
                    STORE_PRODUCTS_MAX_SIZE_KEY,
                    STORE_PRODUCT_COLLECTIONS_MAX_SIZE_KEY,
                    STORE_STAFFS_MAX_SIZE_KEY,
                    STORE_ROLES_MAX_SIZE_KEY,
                    STORE_ADDRESSES_MAX_SIZE_KEY
            );

    // Store scope key set
    public static final Set<String> STORE_SCOPE_KEY_SET =
            Set.of(
                    STORE_MEMBER_AUTO_JOIN_CONDITIONAL_KEY
            );

}
