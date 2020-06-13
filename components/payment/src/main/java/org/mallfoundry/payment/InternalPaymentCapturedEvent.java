package org.mallfoundry.payment;

public class InternalPaymentCapturedEvent extends PaymentEventSupport implements PaymentCapturedEvent {

    public InternalPaymentCapturedEvent(Payment payment) {
        super(payment);
    }
}
