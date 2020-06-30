package org.mallfoundry.autoconfigure.customer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("mall.customer")
public class CustomerProperties {

    private String defaultAvatar;

    private String defaultNickname;
}
