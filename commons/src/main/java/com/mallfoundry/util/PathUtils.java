package com.mallfoundry.util;

import org.apache.commons.io.FilenameUtils;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class PathUtils {

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
}
