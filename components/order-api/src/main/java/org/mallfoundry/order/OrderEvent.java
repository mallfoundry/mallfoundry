package org.mallfoundry.order;

import org.mallfoundry.util.ObjectEvent;

public interface OrderEvent extends ObjectEvent {
    Order getOrder();

    @Override
    default Object getSource() {
        return this.getOrder();
    }
}
