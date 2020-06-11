package org.mallfoundry.payment.methods;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalPaymentMethodQuery implements PaymentMethodQuery {
    private Boolean enabled;
}
