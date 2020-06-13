package org.mallfoundry.payment;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public abstract class PaymentEventSupport extends ApplicationEvent {

    @Getter
    private final Payment payment;

    public PaymentEventSupport(Payment payment) {
        super(payment);
        this.payment = payment;
    }
}
