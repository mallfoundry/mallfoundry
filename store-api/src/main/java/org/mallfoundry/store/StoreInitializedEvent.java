package org.mallfoundry.store;

import java.io.Serializable;

public interface StoreInitializedEvent extends Serializable {

    Store getStore();

}
