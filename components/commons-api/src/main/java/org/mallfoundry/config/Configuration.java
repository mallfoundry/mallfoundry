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

import org.mallfoundry.identity.TenantOwnership;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface Configuration extends TenantOwnership, Serializable {

    String DEFAULT_APPLICATION_ID = "0";

    String getId();

    ConfigurationScope getScope();

    Configuration getParent();

    Configuration createConfiguration(ConfigurationId configId);

    Map<String, String> getProperties();

    Map<String, String> toMap();

    String getProperty(String key);

    String getProperty(String key, String defaultValue);

    void setProperty(String key, Object value);

    void removeProperty(String key);

    String getString(String key);

    String getString(String key, String defaultValue);

    void setString(String key, String value);

    boolean getBoolean(String key);

    boolean getBoolean(String key, boolean defaultValue);

    void setBoolean(String key, boolean value);

    int getInt(String key);

    int getInt(String key, int defaultValue);

    void setInt(String key, int value);

    <T extends Enum<T>> T getEnum(String key, T defaultValue);

    <T extends Enum<T>> void setEnum(String key, T value);
}
