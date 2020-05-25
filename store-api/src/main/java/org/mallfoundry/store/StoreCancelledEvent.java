package org.mallfoundry.store;

import java.io.Serializable;

public interface StoreCancelledEvent extends Serializable {

    Store getStore();

//    Date getOccurredTime();
}
