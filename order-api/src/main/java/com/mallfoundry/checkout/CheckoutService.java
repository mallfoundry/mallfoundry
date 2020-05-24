package com.mallfoundry.checkout;

import java.util.Optional;

public interface CheckoutService {

    Checkout createCheckout();

    Optional<Checkout> getCheckout(String id);

    Checkout checkout(Checkout checkout);
}
