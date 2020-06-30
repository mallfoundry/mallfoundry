package org.mallfoundry.spring.boot.autoconfigure.identity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("mall.identity.user")
public class UserProperties {
    private String defaultUsername;
    private String defaultNickname;
}
