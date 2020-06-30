package org.mallfoundry.identity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultUserConfiguration implements UserConfiguration {
    private String defaultUsername;
    private String defaultNickname;
}
