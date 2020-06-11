package org.mallfoundry.checkout;

import java.util.Optional;

public interface CheckoutService {

    Checkout createCheckout(String id);

    Checkout createCheckout(Checkout checkout);

    Optional<Checkout> getCheckout(String id);

    Checkout updateCheckout(Checkout checkout);

    void deleteCheckout(String id);

    Checkout placeCheckout(String id);
}
