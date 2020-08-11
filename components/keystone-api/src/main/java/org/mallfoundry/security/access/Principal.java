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

import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;

/**
 * 访问控制列表中的主体对象，主体对象可以是用户也可以是角色。
 *
 * @author Zhi Tang
 */
public interface Principal extends Serializable {

    String USER_TYPE = "User";

    String AUTHORITY_TYPE = "Authority";

    String getName();

    String getType();

    Builder toBuilder();

    interface Builder extends ObjectBuilder<Principal> {

        Builder id(String id);

        Builder name(String name);

        Builder type(String type);
    }
}
