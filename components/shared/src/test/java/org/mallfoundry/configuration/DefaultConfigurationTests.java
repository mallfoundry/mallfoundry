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

import org.junit.jupiter.api.Test;
import org.mallfoundry.identity.TenantOwnership;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;

@StandaloneTest
public class DefaultConfigurationTests {

    @Autowired
    private ConfigurationManager manager;

    @Test
    public void testApplicationConfiguration() {
        var appConfId = this.manager.createConfigurationId(ConfigurationScope.APPLICATION, Configuration.DEFAULT_APPLICATION_ID);
        var appConf = this.manager.createConfiguration(appConfId);
        this.manager.saveConfiguration(appConf);
    }

    @Test
    public void testDefaultTenantConfiguration() {
        var appConfId = this.manager.createConfigurationId(ConfigurationScope.APPLICATION, Configuration.DEFAULT_APPLICATION_ID);
        var appConf = this.manager.getConfiguration(appConfId);
        var defaultTenantConfId = this.manager.createConfigurationId(ConfigurationScope.TENANT, TenantOwnership.DEFAULT_TENANT_ID);
        var defaultTenantConf = appConf.createConfiguration(defaultTenantConfId);
        this.manager.saveConfiguration(defaultTenantConf);
    }

    @Test
    public void testSetDefaultTenantConfiguration() {
        var tenantConfId = this.manager.createConfigurationId(ConfigurationScope.TENANT, Configuration.DEFAULT_TENANT_ID);
        var tenantConf = this.manager.getConfiguration(tenantConfId);
        tenantConf.setInt(ConfigurationKeys.ORDER_PLACING_EXPIRES_KEY, 60 * 60 * 1000);
        tenantConf.setString(ConfigurationKeys.ORDER_INVENTORY_DEDUCTION_KEY, "PLACED");
        tenantConf.setBoolean(ConfigurationKeys.ORDER_AUTO_APPROVAL_REFUND_KEY, true);
        tenantConf.setBoolean(ConfigurationKeys.ORDER_AUTO_APPROVAL_REVIEW_KEY, true);

        tenantConf.setProperty(ConfigurationKeys.STORE_DEFAULT_LOGO, "http://static.mallfoundry.org/store/logos/default.png");
        this.manager.saveConfiguration(tenantConf);
    }

}
