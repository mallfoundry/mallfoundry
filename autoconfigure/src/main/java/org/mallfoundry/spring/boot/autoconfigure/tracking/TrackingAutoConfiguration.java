package org.mallfoundry.spring.boot.autoconfigure.tracking;

import org.mallfoundry.tracking.TrackerProvider;
import org.mallfoundry.tracking.provider.KdniaoTrackerProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TrackingProperties.class)
public class TrackingAutoConfiguration {

    private final TrackingProperties properties;

    public TrackingAutoConfiguration(TrackingProperties properties) {
        this.properties = properties;
    }


    @Bean
    @ConditionalOnClass(KdniaoTrackerProvider.class)
    @ConditionalOnProperty(prefix = "mall.tracking", name = "type", havingValue = "kdniao")
    public TrackerProvider trackingProvider() {
        var config = properties.getKdniao();
        return new KdniaoTrackerProvider(config.getUrl(), config.getApiKey(), config.getEBusinessId());
    }

}
