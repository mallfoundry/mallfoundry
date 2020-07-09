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

import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Objects;

public abstract class Plugins {

    @SuppressWarnings("unchecked")
    private static <T> T cast(Object object) {
        return (T) object;
    }

    private static Class<?>[] getPluginInterfaces(List<?> plugins) {
        if (CollectionUtils.isEmpty(plugins)) {
            return cast(new Class<?>[0]);
        }
        return Objects.requireNonNull(CollectionUtils.firstElement(plugins)).getClass().getInterfaces();
    }

    public static <S, T extends Plugin<S>> T consumer(List<T> plugins) {
        var delegatingPlugin = new DelegatingPlugin<S>(cast(plugins));
        return cast(Proxy.newProxyInstance(Plugins.class.getClassLoader(), getPluginInterfaces(plugins), delegatingPlugin));
    }

    private static class DelegatingPlugin<P> implements InvocationHandler, Plugin<P> {

        private final List<Plugin<P>> plugins;

        private DelegatingPlugin(List<Plugin<P>> plugins) {
            this.plugins = plugins;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (var plugin : plugins) {
                method.invoke(plugin, args);
            }
            return null;
        }
    }
}
