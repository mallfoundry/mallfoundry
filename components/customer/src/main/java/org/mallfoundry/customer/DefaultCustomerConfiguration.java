package org.mallfoundry.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultCustomerConfiguration implements CustomerConfiguration {
    private String defaultAvatar;
}
