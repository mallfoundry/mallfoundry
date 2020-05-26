package org.mallfoundry.payment.repository.jpa;

import org.mallfoundry.payment.InternalPayment;
import org.mallfoundry.payment.PaymentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPaymentRepository
        extends PaymentRepository, JpaRepository<InternalPayment, String> {

}
