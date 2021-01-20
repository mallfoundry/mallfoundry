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

import org.mallfoundry.util.ObjectBuilder;
import org.mallfoundry.util.Position;

import java.util.List;

public interface Authority extends Position, ObjectBuilder.ToBuilder<Authority.Builder> {

    String getCode();

    void setCode(String code);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    List<Authority> getChildren();

    void addAuthority(Authority authority);

    void addAuthorities(List<Authority> authorities);

    void removeAuthority(Authority authority);

    void removeAuthorities(List<Authority> authorities);

    interface Builder extends ObjectBuilder<Authority> {

        Builder code(String code);

        Builder name(String name);

        Builder position(int position);

        Builder addAuthority(Authority authority);

        Builder addAuthorities(List<Authority> authorities);
    }
}
