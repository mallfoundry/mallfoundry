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

package org.mallfoundry.store.role;

import org.mallfoundry.ownership.StoreOwnership;
import org.mallfoundry.util.ObjectBuilder;

import java.util.List;

/**
 * 店铺角色对象。
 *
 * @author Zhi Tang
 */
public interface Role extends StoreOwnership, ObjectBuilder.ToBuilder<Role.Builder> {

    String getId();

    void setId(String id);

    void setStoreId(String storeId);

    String getName();

    void setName(String name);

    List<String> getAuthorities();

    void setAuthorities(List<String> authorities);

    interface Builder extends ObjectBuilder<Role> {

        Builder id(String id);

        Builder name(String name);

        Builder authorities(List<String> authorities);
    }
}
