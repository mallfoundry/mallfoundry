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

package org.mallfoundry.config.repository.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.config.ConfigurationId;
import org.mallfoundry.config.ConfigurationScope;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaConfigurationId implements Serializable {

    private String id;

    private ConfigurationScope scope;

    public static JpaConfigurationId of(ConfigurationId id) {
        if (id instanceof JpaConfigurationId) {
            return (JpaConfigurationId) id;
        }
        return new JpaConfigurationId(id.getId(), id.getScope());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JpaConfigurationId that = (JpaConfigurationId) o;
        return Objects.equals(id, that.id) && scope == that.scope;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scope);
    }
}
