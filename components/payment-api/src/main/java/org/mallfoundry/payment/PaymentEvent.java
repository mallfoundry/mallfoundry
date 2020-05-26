package org.mallfoundry.payment;

import org.mallfoundry.util.ObjectEvent;

import java.io.Serializable;

public interface PaymentEvent extends ObjectEvent, Serializable {

    @Override
    default Object getSource() {
        return this.getPayment();
    }

    Payment getPayment();
}
