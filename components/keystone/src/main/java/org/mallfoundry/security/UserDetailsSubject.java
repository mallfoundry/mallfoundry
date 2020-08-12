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

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.identity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsSubject implements UserDetails, Subject {

    private final User user;

    private final Collection<GrantedAuthority> authorities;

    public UserDetailsSubject(User user, Collection<String> authorities) {
        this.user = user;
        this.authorities =
                AuthorityUtils.createAuthorityList(
                        CollectionUtils.emptyIfNull(CollectionUtils.union(user.getAuthorities(), authorities)).toArray(String[]::new));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public String getAvatar() {
        return this.user.getAvatar();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getId() {
        return this.user.getId();
    }

    @Override
    public String getNickname() {
        return this.user.getNickname();
    }

    @Override
    public String getTenantId() {
        return this.user.getTenantId();
    }

    @Override
    public User toUser() {
        return this.user;
    }
}
