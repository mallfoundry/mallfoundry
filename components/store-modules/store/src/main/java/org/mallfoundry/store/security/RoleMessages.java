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

package org.mallfoundry.store.security;

import static org.mallfoundry.i18n.MessageHolder.message;

public abstract class RoleMessages {
    private static final String STORE_SECURITY_SUPER_ROLE_NAME_MESSAGE_CODE_KEY = "store.security.SuperRole.name";
    private static final String STORE_SECURITY_SUPER_ROLE_DESCRIPTION_MESSAGE_CODE_KEY = "store.security.SuperRole.description";

    public static String notFound() {
        return "";
    }

    public abstract static class SuperRole {
        public static String name() {
            return message(STORE_SECURITY_SUPER_ROLE_NAME_MESSAGE_CODE_KEY, "Super Admin");
        }

        public static String description() {
            return message(STORE_SECURITY_SUPER_ROLE_DESCRIPTION_MESSAGE_CODE_KEY, "Have the right to manage the store");
        }
    }
}
