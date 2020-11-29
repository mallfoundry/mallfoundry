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

package org.mallfoundry.storage.acl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class InternalOwner implements Owner {

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type_")
    private OwnerType type;

    @Column(name = "owner_name_")
    private String name;

    public InternalOwner(OwnerType type, String name) {
        this.setType(type);
        this.setName(name);
    }

    public static InternalOwner of(Owner owner) {
        return new InternalOwner(owner.getType(), owner.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InternalOwner)) {
            return false;
        }
        InternalOwner that = (InternalOwner) o;
        return Objects.equals(type, that.type) && Objects.equals(name, that.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }
}
