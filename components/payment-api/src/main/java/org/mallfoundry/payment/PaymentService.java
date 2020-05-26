package org.mallfoundry.payment;

import java.util.Optional;

public interface PaymentService {

    Payment createPayment();

    Instrument createInstrument(String type);

    Payment savePayment(Payment payment);

    void capturePayment(String id) throws PaymentException;

    PaymentNotification validatePayment(String id, Object parameters) throws PaymentException;

    Optional<Payment> getPayment(String id);

    Optional<String> getPaymentRedirectUrl(String id);
}