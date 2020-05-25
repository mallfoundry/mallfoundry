package com.mallfoundry.rest.checkout;

import com.mallfoundry.checkout.Checkout;
import com.mallfoundry.checkout.CheckoutService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1")
@RestController
public class CheckoutResourceV1 {

    private final CheckoutService checkoutService;

    public CheckoutResourceV1(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    private Checkout createCheckout(CheckoutRequest request) {
        var checkout = this.checkoutService.createCheckout();
        request.assignToCheckout(checkout);
        return checkout;
    }

    @PostMapping("/checkouts")
    public Checkout checkout(@RequestBody CheckoutRequest request) {
        return checkoutService.checkout(this.createCheckout(request));
    }
}
