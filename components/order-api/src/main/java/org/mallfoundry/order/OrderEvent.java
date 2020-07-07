package org.mallfoundry.order;

import org.mallfoundry.util.ObjectEvent;

public interface OrderEvent extends ObjectEvent {
    Order getOrder();
}
