package com.mallfoundry.payment;

public interface PendingEvent {

    Payment getPayment();

    long getTimestamp();
}
