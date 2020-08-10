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

package org.mallfoundry.configuration;

import lombok.Setter;

public abstract class ConfigurationHolder {

    @Setter
    private static ConfigurationManager configurationManager;

    public static ConfigurationId createConfigurationId(String tenantId, ConfigurationScope scope, String id) {
        return configurationManager.createConfigurationId(tenantId, scope, id);
    }

    public static Configuration createConfiguration(Object entity) {
        return configurationManager.createConfiguration(configurationManager.createConfigurationId(entity));
    }

    public static Configuration getTenantConfiguration(String tenantId) {
        return configurationManager.getConfiguration(createConfigurationId(null, ConfigurationScope.TENANT, tenantId));
    }

    public static Configuration getConfiguration(Object entity) {
        return configurationManager.getConfiguration(entity);
    }

    public static void saveConfiguration(Configuration configuration) {
        configurationManager.saveConfiguration(configuration);
    }

    public static void deleteConfiguration(Object entity) {
        configurationManager.deleteConfiguration(entity);
    }
}
