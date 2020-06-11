package org.mallfoundry.payment.methods;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodService {

    PaymentMethod createPaymentMethod(String code);

    PaymentMethodQuery createPaymentMethodQuery();

    PaymentMethod addPaymentMethod(PaymentMethod method);

    PaymentMethod updatePaymentMethod(PaymentMethod method);

    List<PaymentMethod> getPaymentMethods(PaymentMethodQuery query);

    Optional<PaymentMethod> getPaymentMethod(String code);

    void deletePaymentMethod(String code);
}
