package com.mallfoundry.payment.rest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PaymentPatchRequest {

    private BigDecimal amount;

    private String reference;

    private Map<String, String> metadata = new HashMap<>();
}
