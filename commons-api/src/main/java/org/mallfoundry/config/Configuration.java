package org.mallfoundry.config;

import java.util.Map;

public interface Configuration {

    String getProperty(String key);

    String getProperty(String key, String defaultValue);

    void setProperty(String key, String value);

    void removeProperty(String key);

    Map<String, String> toMap();
}
