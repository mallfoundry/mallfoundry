package org.mallfoundry.spring.boot.autoconfigure.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.mallfoundry.sms.aliyun.AliyunMessageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration {

    @ConditionalOnClass(IAcsClient.class)
    @ConditionalOnProperty(prefix = "mall.sms", name = "type", havingValue = "aliyun")
    @Bean
    public AliyunMessageService aliyunMessageService(SmsProperties properties) {
        var aliyun = properties.getAliyun();
        var profile = DefaultProfile.getProfile("", aliyun.getAccessKeyId(), aliyun.getAccessSecret());
        var service = new AliyunMessageService(new DefaultAcsClient(profile));
//        service.setSignName(aliyun.getSignName());
//        service.setTemplateCode(aliyun.getTemplateCode());
        return service;
    }
}
