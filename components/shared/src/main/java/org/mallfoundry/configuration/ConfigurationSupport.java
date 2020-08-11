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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class ConfigurationSupport implements Configuration {

    @Override
    public Map<String, String> toMap() {
        var map = new HashMap<String, String>();
        if (Objects.nonNull(this.getParent())) {
            map.putAll(this.getParent().toMap());
        }
        map.putAll(this.getProperties());
        return Collections.unmodifiableMap(map);
    }

    @Override
    public void setProperty(String key, Object value) {
        this.getProperties().put(key, value.toString());
    }

    @Override
    public void removeProperty(String key) {
        this.getProperties().remove(key);
    }

    @Override
    public String getProperty(String key) {
        return this.getProperty(key, null);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        var value = this.getProperties().getOrDefault(key, defaultValue);
        if (Objects.isNull(value) && Objects.nonNull(this.getParent())) {
            return this.getParent().getProperty(key, defaultValue);
        }
        return value;
    }

    @Override
    public String getString(String key) {
        return Objects.toString(this.getProperty(key));
    }

    @Override
    public String getString(String key, String defaultValue) {
        return Objects.toString(this.getProperty(key, defaultValue));
    }
}
