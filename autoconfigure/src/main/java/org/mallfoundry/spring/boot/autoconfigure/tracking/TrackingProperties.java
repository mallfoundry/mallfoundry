package org.mallfoundry.spring.boot.autoconfigure.tracking;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

// mall.tracking.kdniao.e-business-id
// mall.tracking.kdniao.api-key
// mall.tracking.kdniao.url
// mall.tracking.kuaidi100.customer-id
// mall.tracking.kuaidi100.api-key
// mall.tracking.kuaidi100.url
//
@Getter
@Setter
@ConfigurationProperties("mall.tracking")
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
