package org.mallfoundry.spring.boot.autoconfigure.captcha;

import org.mallfoundry.captcha.CaptchaRepository;
import org.mallfoundry.captcha.SmsCaptchaService;
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
    public SmsCaptchaService smsCaptchaService(CaptchaProperties properties,
                                               CaptchaRepository captchaRepository,
                                               MessageService messageService) {
        var sms = properties.getSms();
        var captchaService = new SmsCaptchaService(captchaRepository, messageService);
        captchaService.setCodeLength(properties.getCodeLength());
        captchaService.setSignature(sms.getSignature());
        captchaService.setTemplate(sms.getTemplate());
        return captchaService;
    }
}
