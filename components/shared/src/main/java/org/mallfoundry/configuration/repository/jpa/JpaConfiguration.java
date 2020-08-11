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

package org.mallfoundry.configuration.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.configuration.Configuration;
import org.mallfoundry.configuration.ConfigurationId;
import org.mallfoundry.configuration.ConfigurationScope;
import org.mallfoundry.configuration.ConfigurationSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_config")
@IdClass(JpaConfigurationId.class)
public class JpaConfiguration extends ConfigurationSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Id
    @Column(name = "scope_")
    private ConfigurationScope scope;

    @Column(name = "tenant_id_")
    private String tenantId;

    @ManyToOne(targetEntity = JpaConfiguration.class)
    @JoinColumns({@JoinColumn(name = "parent_id_", referencedColumnName = "id_"),
            @JoinColumn(name = "parent_scope_", referencedColumnName = "scope_"),
    })
    private Configuration parent;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mf_config_property",
            joinColumns = {@JoinColumn(name = "config_id_", referencedColumnName = "id_"),
                    @JoinColumn(name = "config_scope_", referencedColumnName = "scope_")
            })
    @MapKeyColumn(name = "property_key_")
    @Column(name = "property_value_")
    private Map<String, String> properties = new HashMap<>();

    public JpaConfiguration(ConfigurationId configId) {
        this.tenantId = configId.getTenantId();
        this.scope = configId.getScope();
        this.id = configId.getId();
    }

    public static JpaConfiguration of(Configuration config) {
        if (config instanceof JpaConfiguration) {
            return (JpaConfiguration) config;
        }
        var target = new JpaConfiguration();
        BeanUtils.copyProperties(config, target);
        return target;
    }

    @Override
    public Configuration createConfiguration(ConfigurationId configId) {
        var childConfig = new JpaConfiguration(configId);
        childConfig.setParent(this);
        return childConfig;
    }
}
