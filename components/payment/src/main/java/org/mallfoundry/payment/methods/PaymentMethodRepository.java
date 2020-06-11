package org.mallfoundry.payment.methods;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodRepository {

    <S extends InternalPaymentMethod> S save(S entity);

    Optional<InternalPaymentMethod> findById(String s);

    List<InternalPaymentMethod> findAll(PaymentMethodQuery query);

    void delete(InternalPaymentMethod entity);
}
