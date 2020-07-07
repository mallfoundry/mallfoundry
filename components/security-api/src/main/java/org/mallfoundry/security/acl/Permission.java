package org.mallfoundry.security.acl;

import java.io.Serializable;

/**
 * 访问控制权限符。
 *
 * @author Zhi Tang
 */
public interface Permission extends Serializable {

    String getMask();
}
