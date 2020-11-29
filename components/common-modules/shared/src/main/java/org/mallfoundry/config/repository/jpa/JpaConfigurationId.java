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
public class JpaConfigurationId implements Serializable {
    private ConfigurationScope scope;
    private String id;

    public JpaConfigurationId(ConfigurationScope scope, String id) {
        this.scope = scope;
        this.id = id;
    }

    public static JpaConfigurationId of(ConfigurationId id) {
        return new JpaConfigurationId(id.getScope(), id.getId());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaConfigurationId)) {
            return false;
        }
        JpaConfigurationId that = (JpaConfigurationId) object;
        return scope == that.scope && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scope, id);
    }
}
