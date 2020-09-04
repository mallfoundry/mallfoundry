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

    // User key prefix
    private static final String IDENTITY_USER_KEY_PREFIX = "identity.user.";

    // User Property Keys
    public static final String IDENTITY_USER_DEFAULT_USERNAME = IDENTITY_USER_KEY_PREFIX + "default-username";
    public static final String IDENTITY_USER_DEFAULT_AVATAR = IDENTITY_USER_KEY_PREFIX + "default-avatar";
    public static final String IDENTITY_USER_DEFAULT_NICKNAME = IDENTITY_USER_KEY_PREFIX + "default-nickname";

    // Order key prefix
    private static final String ORDER_KEY_PREFIX = "order.";

    // Order Property Keys
    public static final String ORDER_PLACING_EXPIRES_KEY = ORDER_KEY_PREFIX + "placing-expires";
    public static final String ORDER_AUTO_APPROVAL_REFUND_KEY = ORDER_KEY_PREFIX + "auto-approval-refund";
    public static final String ORDER_AUTO_APPROVAL_REVIEW_KEY = ORDER_KEY_PREFIX + "auto-approval-review";
    public static final String ORDER_INVENTORY_DEDUCTION_KEY = ORDER_KEY_PREFIX + "inventory-deduction";

    // Order dispute key prefix
    private static final String ORDER_DISPUTE_KEY_PREFIX = ORDER_KEY_PREFIX + "dispute.";
    public static final String ORDER_DISPUTE_APPLYING_EXPIRES_KEY = ORDER_DISPUTE_KEY_PREFIX + "applying-expires";

    // Store key prefix
    private static final String STORE_KEY_PREFIX = "store.";

    // Store Property Keys
    public static final String STORE_DEFAULT_LOGO = STORE_KEY_PREFIX + "default-logo";
    // Store Super Role Id
    public static final String STORE_SUPER_ROLE_ID = STORE_KEY_PREFIX + "super-role.id";
    //
    public static final String STORE_MEMBER_AUTO_JOIN_CONDITIONAL_KEY = STORE_KEY_PREFIX + "member.auto-join-conditional";
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
