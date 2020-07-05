package org.mallfoundry.security.acl;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootTest
public class AccessControlTests {

    @Autowired
    private AccessControlManager manager;

    @Test
    public void testCreateAccessControl() {
        var principal = this.manager.createPrincipal(Principal.USER_TYPE, "testUser");
        var resource = this.manager.createResource(Resource.STORE_TYPE, "mi");
        var ac = this.manager.createAccessControl(principal, resource);
        var addedAc = this.manager.addAccessControl(ac);
        Assertions.assertTrue(addedAc instanceof MutableAccessControl);
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

    @Transactional
    @Rollback(false)
    @Test
    public void testAddPrincipals() {
        this.manager.addPrincipal(Principal.USER_TYPE, "testUser2");
    }

    @Transactional
    @Rollback(false)
    @Test
    public void testGrantPermission2() {
        var p1 = this.manager.createPermission("read");
        var p2 = this.manager.createPermission("write");
        var resource = this.manager.getResource(Resource.STORE_TYPE, "mi").orElseThrow();
        var principal = this.manager.getPrincipal(Principal.USER_TYPE, "testUser2").orElseThrow();
        this.manager.grantPermissions(Set.of(p1, p2), resource, principal);
    }

    @Transactional
    @Test
    public void testGetAccessControl() {
        var resource = this.manager.getResource(Resource.STORE_TYPE, "mi").orElseThrow();
        var ac = this.manager.getAccessControl(resource);
        System.out.println(ac);
    }

    @Transactional
    @Test
    public void testGetAccessControl2() {
        var principal = this.manager.getPrincipal(Principal.USER_TYPE, "testUser").orElseThrow();
        var resource = this.manager.getResource(Resource.STORE_TYPE, "mi").orElseThrow();
        this.manager.getAccessControl(resource, Set.of(principal));
    }


    @Transactional
    @Test
    public void testGranted() {
        var p1 = this.manager.createPermission("read");
        var principal = this.manager.getPrincipal(Principal.USER_TYPE, "testUser").orElseThrow();
        var resource = this.manager.getResource(Resource.STORE_TYPE, "mi").orElseThrow();
        var ac = this.manager.getAccessControl(resource, Set.of(principal)).orElseThrow();
        var readGranted = ac.granted(p1, principal);
        Assert.assertTrue(readGranted);
    }
}
