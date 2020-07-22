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

import java.util.Set;

public interface AccessControlAuthorizer {

    boolean hasPermission(Principal principal, Resource resource, Permission permission);

    boolean hasPermission(Set<Principal> principals, Resource resource, Permission permission);

    boolean hasAnyPermissions(Principal principal, Resource resource, Set<Permission> permissions);

    boolean hasAnyPermissions(Set<Principal> principals, Resource resource, Set<Permission> permissions);

    void checkPermission(Principal principal, Resource resource, Permission permission) throws AccessDeniedException;

    void checkPermission(Set<Principal> principals, Resource resource, Permission permission) throws AccessDeniedException;

    void checkAnyPermissions(Principal principal, Resource resource, Set<Permission> permissions) throws AccessDeniedException;

    void checkAnyPermissions(Set<Principal> principals, Resource resource, Set<Permission> permissions) throws AccessDeniedException;
}