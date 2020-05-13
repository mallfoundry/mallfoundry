package com.mallfoundry.payment;

public interface PaymentSource {

    String getType();

    void setType(String type);
}
