package org.mallfoundry.spring.boot.autoconfigure.captcha;


import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.captcha.CaptchaType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
@Setter
@ConfigurationProperties("mall.captcha")
public class CaptchaProperties {

    private CaptchaType type;

    private int codeLength;

    @NestedConfigurationProperty
    private Sms sms = new Sms();

    @Getter
    @Setter
    static class Sms {
        private String template;
        private String signature;
        private int expires;
        private int intervals;
    }

}
