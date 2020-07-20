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

package org.mallfoundry.security.acl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ImmutablePermission implements Permission {

    private String mask;

    public ImmutablePermission(String mask) {
        this.mask = mask;
    }

    public static ImmutablePermission of(Permission permission) {
        if (permission instanceof ImmutablePermission) {
            return (ImmutablePermission) permission;
        }
        return new ImmutablePermission(permission.getMask());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImmutablePermission that = (ImmutablePermission) o;
        return Objects.equals(mask, that.mask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mask);
    }
}
