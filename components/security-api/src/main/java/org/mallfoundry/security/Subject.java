package org.mallfoundry.security;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 提供 Spring security 必须的 {@code UserDetails} 信息，
 * 并对 {@code Identity User} 的信息进行增强。
 *
 * @author Zhi Tang
 */
public interface Subject extends UserDetails {

    /**
     * 获得用户标识。
     *
     * @return 用户标识
     */
    String getId();

    String getUsername();

    String getNickname();
}
