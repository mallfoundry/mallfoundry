package org.mallfoundry.checkout;

public interface CheckoutService {

    Checkout createCheckout();

    Checkout checkout(Checkout checkout);
}
