package org.mallfoundry.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EnvironmentConfiguration implements Configuration {

    private final Environment environment;

    public EnvironmentConfiguration(Environment environment) {
        this.environment = environment;
    }


    @Override
    public String getProperty(String key) {
        return null;
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return null;
    }

    @Override
    public void setProperty(String key, String value) {

    }

    @Override
    public void removeProperty(String key) {

    }

    @Override
    public Map<String, String> toMap() {
        return null;
    }
}
