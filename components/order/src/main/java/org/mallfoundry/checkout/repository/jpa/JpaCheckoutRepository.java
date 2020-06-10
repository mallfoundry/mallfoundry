package org.mallfoundry.checkout.repository.jpa;

import org.mallfoundry.checkout.CheckoutRepository;
import org.mallfoundry.checkout.InternalCheckout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCheckoutRepository
        extends JpaRepository<InternalCheckout, String>, CheckoutRepository {
}
