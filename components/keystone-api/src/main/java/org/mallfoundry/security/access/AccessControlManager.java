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

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface AccessControlManager {

    Principal createPrincipal(String type, String name);

    void removePrincipal(Principal principal);

    void removePrincipals(List<Principal> principals);

    Resource createResource(Object resource);

    Resource createResource(String type, Serializable identifier);

    void removeResource(Resource resource);

    AccessControl createAccessControl(Resource resource);

    AccessControl createAccessControl(Principal owner, Resource resource);

    AccessControl addAccessControl(AccessControl accessControl);

    AccessControl getAccessControl(Resource resource);

    AccessControl getAccessControl(Resource resource, Set<Principal> principals);

    void grantAccessControl(AccessControl accessControl);

    void revokeAccessControl(AccessControl accessControl);

    void deleteAccessControl(AccessControl accessControl);
}
