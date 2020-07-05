package org.mallfoundry.security.acl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 访问控制对象适用于访问控制列表({@code ACL})，提供了细粒度的鉴权功能。
 * 访问控制对象是以资源({@link Resource})对象为中心，
 * grant read,write on resource to principal{user|authority}
 * grant read,write on resource to role
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
