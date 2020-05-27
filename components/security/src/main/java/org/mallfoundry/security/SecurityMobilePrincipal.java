package org.mallfoundry.security;

import lombok.Getter;
import org.mallfoundry.identity.MobilePrincipal;

public class SecurityMobilePrincipal implements MobilePrincipal {

    @Getter
    private final String name;

    SecurityMobilePrincipal(String name) {
        this.name = name;
    }
}
