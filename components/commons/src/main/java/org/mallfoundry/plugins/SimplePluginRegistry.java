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

package org.mallfoundry.plugins;

import org.springframework.data.util.CastUtils;

import java.util.List;
import java.util.stream.Collectors;

public class SimplePluginRegistry implements PluginRegistry {

    private final List<Plugin<?>> plugins;

    public SimplePluginRegistry(List<Plugin<?>> plugins) {
        this.plugins = plugins;
    }

    @Override
    public <S, T extends Plugin<S>> List<T> getPlugins(Class<T> clazz) {
        return CastUtils.cast(
                this.plugins.stream()
                        .filter(plugin -> clazz.isAssignableFrom(plugin.getClass()))
                        .collect(Collectors.toUnmodifiableList()));
    }
}
