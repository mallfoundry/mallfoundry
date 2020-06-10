package org.mallfoundry.rest.checkout;

import org.mallfoundry.checkout.Checkout;
import org.mallfoundry.checkout.CheckoutService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/v1")
@RestController
public class CheckoutResourceV1 {

    private final CheckoutService checkoutService;

    public CheckoutResourceV1(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    private Checkout createCheckout(CheckoutRequest request) {
        return checkoutService.createCheckout(
                request.assignToCheckout(
                        this.checkoutService.createCheckout((String) null)));
    }

    @PostMapping("/checkouts")
    public Checkout checkout(@RequestBody CheckoutRequest request) {
        return checkoutService.createCheckout(this.createCheckout(request));
    }

    @GetMapping("/checkouts/{id}")
    public Optional<Checkout> getCheckout(@PathVariable("id") String checkoutId) {
        return checkoutService.getCheckout(checkoutId);
    }

    @PostMapping("/checkouts/{id}/place")
    public void placeCheckout(@PathVariable("id") String checkoutId) {
        checkoutService.placeCheckout(checkoutId);
    }
}
