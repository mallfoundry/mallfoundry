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

package org.mallfoundry.security;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

import static org.mallfoundry.i18n.MessageHolder.message;

/**
 * 获得当前上下文中的用户对象。
 *
 * @author Zhi Tang
 */
public abstract class SubjectHolder {

    public static boolean isAuthenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(authentication) && authentication.isAuthenticated();
    }

    private static Subject requiredPrincipal() {
        if (!isAuthenticated()) {
            throw new SecurityException(
                    message("security.subject.notAuthenticated",
                            "The current user is not authenticated"));
        }
        return (Subject) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUserId() {
        return requiredPrincipal().getId();
    }

    public static String getUsername() {
        return requiredPrincipal().getUsername();
    }

    public static String getNickname() {
        return requiredPrincipal().getNickname();
    }

    /**
     * 切换到系统用户。
     */
    public static SystemUser switchToSystemUser() {
        return SystemUsers.switcher().switchTo();
    }
}
