package org.mallfoundry.payment;

import java.io.Serializable;

public interface Instrument extends Serializable {

    String getType();

    void setType(String type);
}
