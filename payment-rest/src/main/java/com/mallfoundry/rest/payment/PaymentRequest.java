package com.mallfoundry.rest.payment;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class PaymentRequest {

    private PaymentSourceRequest source = new PaymentSourceRequest();

    private BigDecimal amount;

    private String reference;

    private Map<String, String> metadata = new HashMap<>();

    @Getter
    @Setter
    static class PaymentSourceRequest {
        private String type;
    }
}
