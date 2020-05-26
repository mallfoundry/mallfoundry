package org.mallfoundry.payment;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class InternalCapturedEvent extends ApplicationEvent implements CapturedEvent {

    @Getter
    private final Payment payment;

    public InternalCapturedEvent(Payment payment) {
        super(payment);
        this.payment = payment;
    }
}
