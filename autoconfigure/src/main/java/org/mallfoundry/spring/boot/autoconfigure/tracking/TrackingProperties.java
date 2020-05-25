package org.mallfoundry.spring.boot.autoconfigure.tracking;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

// mall.tracker.kdniao.e-business-id
// mall.tracker.kdniao.api-key
// mall.tracker.kdniao.url
// mall.tracker.kuaidi100.customer-id
// mall.tracker.kuaidi100.api-key
// mall.tracker.kuaidi100.url
//
@Getter
@Setter
@ConfigurationProperties("mall.tracker")
public class TrackingProperties {

    TrackingType type;

    @NestedConfigurationProperty
    private Kdniao kdniao;

    @Getter
    @Setter
    static class Kdniao {
        private String url;
        private String eBusinessId;
        private String apiKey;
    }

    enum TrackingType {
        KDNIAO
    }
}
