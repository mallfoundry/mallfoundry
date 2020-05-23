package com.mallfoundry.checkout;

import com.mallfoundry.store.product.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InternalCheckoutService implements CheckoutService {

    private final ProductService productService;

    public InternalCheckoutService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Checkout createCheckout() {
        return null;
    }

    @Override
    public Optional<Checkout> getCheckout(String id) {
        return Optional.empty();
    }

    @Override
    public Checkout checkout(Checkout checkout) {

//        this.productService.
        return null;
    }

    @Override
    public Checkout saveCheckout(Checkout checkout) {
        return null;
    }
}
