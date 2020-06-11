package org.mallfoundry.rest.payment.methods;

import org.mallfoundry.payment.methods.PaymentMethod;
import org.mallfoundry.payment.methods.PaymentMethodService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class PaymentMethodResourceV1 {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodResourceV1(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping("/payments/methods")
    public List<PaymentMethod> getPaymentMethods() {
        return this.paymentMethodService.getPaymentMethods(
                this.paymentMethodService.createPaymentMethodQuery());
    }
}
