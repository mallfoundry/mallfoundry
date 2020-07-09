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

package org.mallfoundry.security.acl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 访问控制对象适用于访问控制列表({@code ACL})，提供了细粒度的鉴权功能。
 * 访问控制对象是以资源({@link Resource})对象为中心。
 * grant read,write on resource to principal{user|authority}
 *
 * @author Zhi Tang
 */
public interface AccessControl extends Serializable {

    Resource getResource();

    Principal getOwner();

    AccessControl getParent();

    boolean isInherit();

    void setInherit(boolean inherit);

    List<AccessControlEntry> getEntries();

    void grant(Permission permission, Principal principal);

    void grants(Set<Permission> permissions, Principal principal);

    void revoke(Permission permission, Principal principal);

    void revoke(Set<Permission> permissions, Principal principal);

    boolean granted(Permission permission, Principal principal);

    boolean granted(Set<Permission> permissions, Principal principal);

    boolean granted(Set<Permission> permissions, Set<Principal> principals);
}
