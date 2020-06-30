package org.mallfoundry.test;

public abstract class StaticUtils {

    private static final String BASE_URL = "http://static.mallfoundry.org";

    public static String resolve(String url) {
        return BASE_URL + url;
    }
}
