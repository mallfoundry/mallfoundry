package com.mallfoundry.payment;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class InternalPendingEvent extends ApplicationEvent implements PendingEvent {

    @Getter
    private final Payment payment;

    public InternalPendingEvent(Payment payment) {
        super(payment);
        this.payment = payment;
    }
}
