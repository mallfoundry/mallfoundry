package com.mallfoundry.rest.store;

import java.io.Serializable;

public interface StoreCancelledEvent extends Serializable {

    Store getStore();

//    Date getOccurredTime();
}
