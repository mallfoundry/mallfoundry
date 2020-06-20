package org.mallfoundry.spring.boot.autoconfigure.sms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
@Setter
@ConfigurationProperties("mall.sms")
public class SmsProperties {

    private SmsType type;

    @NestedConfigurationProperty
    private Aliyun aliyun = new Aliyun();

    public enum SmsType {
        Aliyun
    }

    @Getter
    @Setter
    static class Aliyun {

        private String accessKeyId;

        private String accessKeySecret;
    }

}
