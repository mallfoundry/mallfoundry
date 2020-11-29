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

package org.mallfoundry.security.access;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;

import java.util.Set;

public abstract class AccessControlEntrySupport implements MutableAccessControlEntry {

    public static AccessControlEntrySupport of(AccessControlEntry ace) {
        return (AccessControlEntrySupport) ace;
    }

    @Override
    public void addPermission(String permission) {
        if (!this.checkPermission(permission)) {
            this.getPermissions().add(permission);
        }
    }

    @Override
    public void addPermissions(Set<String> permissions) {
        SetUtils.emptyIfNull(permissions).forEach(this::addPermission);
    }

    @Override
    public void removePermission(String permission) {
        this.getPermissions().remove(permission);
    }

    @Override
    public void removePermissions(Set<String> permissions) {
        this.getPermissions().removeAll(permissions);
    }

    @Override
    public void clearPermissions() {
        this.getPermissions().clear();
    }

    @Override
    public boolean checkPermission(String permission) {
        return this.getPermissions().contains(permission);
    }

    @Override
    public boolean checkAnyPermission(Set<String> permissions) {
        return CollectionUtils.containsAny(this.getPermissions(), permissions);
    }
}
