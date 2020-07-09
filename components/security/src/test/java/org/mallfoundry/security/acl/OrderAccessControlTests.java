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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootTest
public class OrderAccessControlTests {

    @Autowired
    private AccessControlManager manager;

    @Test
    public void testAddPrincipals() {
        this.manager.addPrincipal(Principal.USER_TYPE, "1");
        this.manager.addPrincipal(Principal.USER_TYPE, "2");
        this.manager.addResource(Resource.CUSTOMER_TYPE, "1");
    }

    //order_browse
    @Test
    public void testGrantOrderBrowse() {
        var resourceOwner = this.manager.getPrincipal(Principal.USER_TYPE, "1").orElseThrow();
        var resource = this.manager.getResource(Resource.CUSTOMER_TYPE, "1").orElseThrow();
        this.manager.addAccessControl(this.manager.createAccessControl(resourceOwner, resource));
        var accessor = this.manager.createPrincipal(Principal.USER_TYPE, "1");
        var p1 = this.manager.createPermission("order_browse");
        manager.grantPermission(p1, resource, accessor);
    }

    @Transactional
    @Rollback(false)
    @Test
    public void testGrantPermission() {
        var p1 = this.manager.createPermission("read");
        var p2 = this.manager.createPermission("write");
        var resource = this.manager.getResource(Resource.STORE_TYPE, "mi").orElseThrow();
        var principal = this.manager.getPrincipal(Principal.USER_TYPE, "testUser").orElseThrow();
        this.manager.grantPermissions(Set.of(p1, p2), resource, principal);
    }
}
