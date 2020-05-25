package org.mallfoundry.spring.boot.autoconfigure.tracking;

import org.mallfoundry.tracking.TrackingProvider;
import org.mallfoundry.tracking.provider.KdniaoTrackingProvider;
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
    @ConditionalOnClass(KdniaoTrackingProvider.class)
    @ConditionalOnProperty(prefix = "mall.tracking", name = "type", havingValue = "kdniao")
    public TrackingProvider trackingProvider() {
        var config = properties.getKdniao();
        return new KdniaoTrackingProvider(config.getUrl(), config.getApiKey(), config.getEBusinessId());
    }

}
