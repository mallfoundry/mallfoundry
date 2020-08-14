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

public interface AuthorityDescription extends Position, ObjectBuilder.ToBuilder<AuthorityDescription.Builder> {

    String getAuthority();

    void setAuthority(String authority);

    String getLanguage();

    void setLanguage(String language);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    List<AuthorityDescription> getChildren();

    void addAuthority(AuthorityDescription authority);

    void addAuthorities(List<AuthorityDescription> authorities);

    void removeAuthority(AuthorityDescription authority);

    void removeAuthorities(List<AuthorityDescription> authorities);

    interface Builder extends ObjectBuilder<AuthorityDescription> {

        Builder authority(String authority);

        Builder language(String language);

        Builder name(String name);

        Builder position(int position);

        Builder addAuthority(AuthorityDescription authority);

        Builder addAuthorities(List<AuthorityDescription> authorities);
    }
}
