package com.mallfoundry.payment;

import com.mallfoundry.util.ObjectEvent;

import java.io.Serializable;

public interface PaymentEvent extends ObjectEvent, Serializable {

    @Override
    default Object getSource() {
        return this.getPayment();
    }

    Payment getPayment();
}
