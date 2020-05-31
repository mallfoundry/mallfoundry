package org.mallfoundry.payment;

import java.util.Optional;

public interface PaymentService {

    Payment createPayment(String id);

    Instrument createInstrument(String type);

    Payment createPayment(Payment payment);

    void capturePayment(String id) throws PaymentException;

    PaymentNotification validatePayment(String id, Object parameters) throws PaymentException;

    Optional<Payment> getPayment(String id);

    Optional<String> getPaymentRedirectUrl(String id);
}
