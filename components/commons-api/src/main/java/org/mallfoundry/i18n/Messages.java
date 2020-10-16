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

package org.mallfoundry.i18n;

import java.util.HashMap;
import java.util.Map;

/**
 * Simplify message code key that begins with the {@code org.mallfoundry} package name.
 *
 * @author Tang Zhi
 */
public abstract class Messages {

    private static final Map<String, MessageKeys> KEYS_CACHE = new HashMap<>();

    public static MessageKeys getKeys(Class<?> clazz) {
        return getKeys(clazz.getName());
    }

    public static MessageKeys getKeys(String baseName) {
        return KEYS_CACHE.computeIfAbsent(baseName, MessageKeys::new);
    }
}
