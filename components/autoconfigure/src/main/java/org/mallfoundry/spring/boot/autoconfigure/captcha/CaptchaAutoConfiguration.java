package org.mallfoundry.spring.boot.autoconfigure.captcha;

import org.mallfoundry.captcha.CaptchaRepository;
import org.mallfoundry.captcha.SmsCaptchaProvider;
import org.mallfoundry.sms.MessageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaAutoConfiguration {

    @ConditionalOnProperty(prefix = "mall.captcha", name = "type", havingValue = "sms")
    @Bean
    public SmsCaptchaProvider smsCaptchaService(CaptchaProperties properties, MessageService messageService,
                                                CaptchaRepository captchaRepository) {
        var sms = properties.getSms();
        var provider = new SmsCaptchaProvider(messageService, captchaRepository);
        provider.setSignature(sms.getSignature());
        provider.setTemplate(sms.getTemplate());
        provider.setExpires(sms.getExpires());
        provider.setIntervals(sms.getIntervals());
        return provider;
    }
}
