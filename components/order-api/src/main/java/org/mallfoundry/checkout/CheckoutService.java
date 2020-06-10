package org.mallfoundry.checkout;

import java.util.Optional;

public interface CheckoutService {

    Checkout createCheckout(String id);

    Checkout createCheckout(Checkout checkout);

    Optional<Checkout> getCheckout(String id);

    void deleteCheckout(String id);

    void placeCheckout(String id);
}
