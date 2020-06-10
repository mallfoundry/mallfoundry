package org.mallfoundry.checkout;

import java.util.Optional;

public interface CheckoutRepository {

    Optional<InternalCheckout> findById(String id);

    <S extends InternalCheckout> S save(S entity);

    void delete(InternalCheckout checkout);
}
