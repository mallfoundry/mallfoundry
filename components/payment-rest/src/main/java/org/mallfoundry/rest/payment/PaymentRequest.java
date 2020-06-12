package org.mallfoundry.rest.payment;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.payment.Payment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class PaymentRequest {

    private PaymentInstrumentRequest instrument = new PaymentInstrumentRequest();

    private String returnUrl;

    private BigDecimal amount;

    private String reference;

    private Map<String, String> metadata = new HashMap<>();

    @Getter
    @Setter
    static class PaymentInstrumentRequest {
        private String type;
    }

    public Payment assignPayment(Payment payment) {
        return payment.toBuilder()
                .amount(this.getAmount())
                .returnUrl(this.getReturnUrl())
                .reference(this.getReference())
                .metadata(this.getMetadata())
                .instrument(payment.createInstrument(this.getInstrument().getType()))
                .build();
    }
}
