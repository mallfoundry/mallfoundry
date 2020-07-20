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
