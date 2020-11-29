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
    public static final String STORE_CREATE = "store_create";
    public static final String STORE_READ = "store_read";
    public static final String STORE_WRITE = "store_write";
    public static final String STORE_MANAGE = "store_manage";

    // Store address Authorities
    public static final String STORE_ADDRESS_ADD = "store_address_add";
    public static final String STORE_ADDRESS_READ = "store_address_read";
    public static final String STORE_ADDRESS_WRITE = "store_address_write";
    public static final String STORE_ADDRESS_DELETE = "store_address_delete";
    public static final String STORE_ADDRESS_MANAGE = "store_address_manage";

    // Store staff Authorities
    public static final String STORE_STAFF_ADD = "store_staff_add";
    public static final String STORE_STAFF_READ = "store_staff_read";
    public static final String STORE_STAFF_WRITE = "store_staff_write";
    public static final String STORE_STAFF_DELETE = "store_staff_delete";
    public static final String STORE_STAFF_MANAGE = "store_staff_manage";

    // Store role Authorities
    public static final String STORE_ROLE_ADD = "store_role_add";
    public static final String STORE_ROLE_READ = "store_role_read";
    public static final String STORE_ROLE_WRITE = "store_role_write";
    public static final String STORE_ROLE_DELETE = "store_role_delete";
    public static final String STORE_ROLE_MANAGE = "store_role_manage";

    // Store member Authorities
    public static final String STORE_MEMBER_ADD = "store_member_add";
    public static final String STORE_MEMBER_READ = "store_member_read";
    public static final String STORE_MEMBER_WRITE = "store_member_write";
    public static final String STORE_MEMBER_DELETE = "store_member_delete";
    public static final String STORE_MEMBER_MANAGE = "store_member_manage";
}
