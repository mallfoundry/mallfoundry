package com.mallfoundry.payment.repository.jpa;

import com.mallfoundry.payment.InternalPayment;
import com.mallfoundry.payment.PaymentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPaymentRepository
        extends PaymentRepository, JpaRepository<InternalPayment, String> {

}
