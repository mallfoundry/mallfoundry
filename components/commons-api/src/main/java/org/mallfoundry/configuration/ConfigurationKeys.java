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

public abstract class ConfigurationKeys {
    public static final String H5_SITE_URL = "mall.h5.site.url";


    // store.member.auto-join-conditional=order_placed
// store.member.auto-join-conditional=order_paid

    // tenant.stores.max_size=200
    public abstract static class Tenant {

    }

    // store.products.max_size=200
    public abstract static class Store {
        static final String STORE_PREFIX = "store";

        public abstract static class Member {
            static final String STORE_MEMBER_PREFIX = STORE_PREFIX + ".member";
            public static final String AUTO_JOIN_CONDITIONAL = STORE_MEMBER_PREFIX + ".auto-join-conditional";
        }
    }
}
