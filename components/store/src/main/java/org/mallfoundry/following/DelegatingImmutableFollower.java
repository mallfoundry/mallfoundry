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

package org.mallfoundry.following;

import org.mallfoundry.identity.Gender;
import org.mallfoundry.identity.User;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DelegatingImmutableFollower implements ImmutableFollower {

    private final User user;

    public DelegatingImmutableFollower(User user) {
        this.user = user;
    }

    @Override
    public String getId() {
        return this.user.getId();
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
    public String getNickname() {
        return this.user.getNickname();
    }

    @Override
    public Gender getGender() {
        return this.user.getGender();
    }

    @Override
    public String getCountryCode() {
        return this.user.getCountryCode();
    }

    @Override
    public String getPhone() {
        return this.user.getPhone();
    }

    @Override
    public String getEmail() {
        return this.user.getEmail();
    }

    @Override
    public boolean isImmutable() {
        return this.user.isImmutable();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public List<String> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Date getCreatedTime() {
        return this.user.getCreatedTime();
    }

    @Override
    public String getTenantId() {
        return this.user.getTenantId();
    }
}
