package com.mallfoundry.store;

import java.io.Serializable;
import java.util.Date;

public interface StoreCancelledEvent extends Serializable {

    Store getStore();

//    Date getOccurredTime();
}
