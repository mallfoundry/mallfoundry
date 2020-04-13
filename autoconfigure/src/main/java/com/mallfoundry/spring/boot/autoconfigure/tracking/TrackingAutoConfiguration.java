package com.mallfoundry.spring.boot.autoconfigure.tracking;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(TrackingProperties.class)
public class TrackingAutoConfiguration {

    private final TrackingProperties properties;

    public TrackingAutoConfiguration(TrackingProperties properties) {
        this.properties = properties;
    }

}
