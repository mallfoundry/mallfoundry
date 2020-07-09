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

package org.mallfoundry.util;

import org.apache.commons.io.FilenameUtils;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class PathUtils {

    public static final String PATH_SEPARATOR = "/";

    public static final String ROOT_PATH = PATH_SEPARATOR;

    public static String normalize(String path) {
        return Optional.of(path)
                .map(s -> FilenameUtils.normalize(s, true))
                .map(s -> s.replaceAll("/+", "/"))
                .map(s -> s.startsWith("/") ? s : "/" + s)
                .orElseThrow();
    }

    public static String concat(String first, String... more) {
        return normalize(concat0(first, more));
    }

    private static String concat0(String first, String... more) {
        String path = Stream.concat(Stream.of(first), Stream.of(more))
                .map(Object::toString)
                .reduce((a, b) -> String.join("/", a, b))
                .orElseThrow();
        return FilenameUtils.normalize(path, true);
    }

    public static String removePrefixSeparator(String path) {
        return Optional.of(path).map(PathUtils::normalize)
                .map(s -> s.startsWith("/") ? s.substring(1) : s)
                .orElseThrow();
    }
}
