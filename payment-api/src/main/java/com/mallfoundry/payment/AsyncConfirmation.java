package com.mallfoundry.payment;

public interface AsyncConfirmation {

    PaymentStatus getStatus();

    Object getBody();
}
