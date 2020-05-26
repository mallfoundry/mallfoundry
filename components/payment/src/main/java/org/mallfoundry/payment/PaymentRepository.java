package org.mallfoundry.payment;

import java.util.Optional;

public interface PaymentRepository {

    <S extends InternalPayment> S save(S s);

    Optional<InternalPayment> findById(String id);
}
