package org.mallfoundry.payment;

import java.io.Serializable;

public interface PaymentInstrument extends Serializable {

    String getType();

    void setType(String type);
}
