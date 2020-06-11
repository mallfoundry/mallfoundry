package org.mallfoundry.payment;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class InternalPendingEvent extends ApplicationEvent implements PaymentPendingEvent {

    @Getter
    private final Payment payment;

    public InternalPendingEvent(Payment payment) {
        super(payment);
        this.payment = payment;
    }
}
