package org.mallfoundry.payment;

public class InternalPaymentPendingEvent extends PaymentEventSupport implements PaymentPendingEvent {

    public InternalPaymentPendingEvent(Payment payment) {
        super(payment);
    }
}
