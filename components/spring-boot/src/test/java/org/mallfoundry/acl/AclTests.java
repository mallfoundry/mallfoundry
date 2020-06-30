package org.mallfoundry.acl;

import org.mallfoundry.identity.repository.jpa.JpaUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class AclTests {

    @Autowired
    private MutableAclService aclService;


    @Transactional
    @Rollback(false)
    @Test
    @WithUserDetails("tangzhi")
    public void testInsert() {
        ObjectIdentity oi = new ObjectIdentityImpl(JpaUser.class, 1);
//        MutableAcl acl = this.aclService.createAcl(oi);
        MutableAcl acl = (MutableAcl) this.aclService.readAclById(oi);

        Sid sid = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, sid, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.CREATE, sid, true);


        acl = aclService.updateAcl(acl);

//        acl.setParent();
//        acl.
    }

    @Transactional
    @Rollback(false)
    @Test
    @WithUserDetails("tangzhi2")
    public void testInsert2() {
        ObjectIdentity oi = new ObjectIdentityImpl(JpaUser.class, 1);
        MutableAcl acl = (MutableAcl) this.aclService.readAclById(oi);
        Sid sid = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, sid, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.CREATE, sid, true);
        acl = aclService.updateAcl(acl);

//        acl.setParent();
//        acl.
    }

    @Transactional
    @Rollback(false)
    @Test
    @WithUserDetails("tangzhi")
    public void test() {
        ObjectIdentity oi = new ObjectIdentityImpl(JpaUser.class, 1);
        MutableAcl acl = (MutableAcl) this.aclService.readAclById(oi);
        List<Sid> sids = List.of(new PrincipalSid(SecurityContextHolder.getContext().getAuthentication()));
        var granted = acl.isGranted(List.of(BasePermission.CREATE), sids, true);
        System.out.println(granted);
    }
}
