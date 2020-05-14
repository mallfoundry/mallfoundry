package com.mallfoundry.payment;

import java.util.Optional;

public interface PaymentService {

    Payment createPayment();

    PaymentSource createPaymentSource(String type);

    Payment savePayment(Payment payment);

    void capturePayment(String id) throws PaymentException;

    PaymentNotification validatePayment(String id, Object parameters) throws PaymentException;

    Optional<Payment> getPayment(String id);

    Optional<String> getPaymentRedirectUrl(String id);
}
