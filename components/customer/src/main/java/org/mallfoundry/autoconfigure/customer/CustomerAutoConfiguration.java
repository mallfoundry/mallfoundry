package org.mallfoundry.autoconfigure.customer;

import org.mallfoundry.customer.CustomerConfiguration;
import org.mallfoundry.customer.DefaultCustomerConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(CustomerProperties.class)
@Configuration
public class CustomerAutoConfiguration {

    @Bean
    public CustomerConfiguration customerConfiguration(CustomerProperties properties) {
        var config = new DefaultCustomerConfiguration();
        config.setDefaultAvatar(properties.getDefaultAvatar());
        return config;
    }
}
