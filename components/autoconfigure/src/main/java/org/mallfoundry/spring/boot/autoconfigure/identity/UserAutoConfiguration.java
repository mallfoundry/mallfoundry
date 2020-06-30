package org.mallfoundry.spring.boot.autoconfigure.identity;


import org.mallfoundry.identity.DefaultUserConfiguration;
import org.mallfoundry.identity.UserConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(UserProperties.class)
@Configuration
public class UserAutoConfiguration {

    @Bean
    public UserConfiguration userConfiguration(UserProperties properties) {
        var config = new DefaultUserConfiguration();
        config.setDefaultUsername(properties.getDefaultUsername());
        config.setDefaultNickname(properties.getDefaultNickname());
        return config;
    }
}
