package org.mallfoundry.acl;

import org.springframework.security.acls.domain.BasePermission;

public class AclPermissionTests {
    public static void main(String[] args) {
        System.out.println(BasePermission.READ.getPattern());
        System.out.println(BasePermission.WRITE.getPattern());
        System.out.println(BasePermission.CREATE.getPattern());
        System.out.println(BasePermission.DELETE.getPattern());
        System.out.println(BasePermission.ADMINISTRATION.getPattern());
    }
}
