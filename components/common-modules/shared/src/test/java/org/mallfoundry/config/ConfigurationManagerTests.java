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

package org.mallfoundry.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mallfoundry.test.StandaloneTest;
import org.mallfoundry.util.ObjectType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@StandaloneTest
public class ConfigurationManagerTests {

    @Autowired
    private ConfigurationManager manager;

    @Test
    public void testSaveConfig() {
        var cId = this.manager.createConfigurationId(ObjectType.STORE, "1");
        var config = this.manager.createConfiguration(cId);
        config.setProperty("abc", "abc");
        this.manager.saveConfiguration(config);
    }

    @Test
    public void testGetAndSaveConfig() {
        var cId = this.manager.createConfigurationId(ObjectType.STORE, "1");
        var config = this.manager.getConfiguration(cId);
        config.setProperty("abc1", "abc");
        this.manager.saveConfiguration(config);
    }

    @Test
    public void testGetAndSaveConfigProperties() {
        var cId = this.manager.createConfigurationId(ObjectType.STORE, "1");
        var config = this.manager.getConfiguration(cId);
        config.setProperty("abc1", "abc");
        config.setProperty("abc2", "abc");
        config.setProperty("abc3", "abc");
        config.setProperty("abc", "cba");
        this.manager.saveConfiguration(config);
    }

    @Test
    public void testGetConfigToMap() {
        var cId = this.manager.createConfigurationId(ObjectType.STORE, "1");
        var map = this.manager.getConfiguration(cId).toMap();
        Assertions.assertThat(map).isInstanceOf(Map.class);
    }
}
