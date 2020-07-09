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

package org.mallfoundry.store;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StoreMapConfiguration extends ConcurrentHashMap<String, String> implements StoreConfiguration {

    public StoreMapConfiguration(Map<String, String> map) {
        this.putAll(map);
    }

    @Override
    public String get(String name) {
        return this.get((Object) name);
    }

    @Override
    public String get(String name, String defaultValue) {
        return this.getOrDefault(name, defaultValue);
    }

    @Override
    public void set(String name, String value) {
        this.put(name, value);
    }

    @Override
    public void remove(String name) {
        this.remove((Object) name);
    }

    @Override
    public Map<String, String> toMap() {
        return Collections.unmodifiableMap(this);
    }
}
