package org.mallfoundry.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class CaseUtils {

    public static String camelCase(String string) {
        var strings = StringUtils.split(string, "_");
        var capString = Arrays.stream(strings).map(StringUtils::capitalize).collect(Collectors.joining());
        return StringUtils.uncapitalize(capString);
    }
}
