package org.mallfoundry.order;

import org.mallfoundry.util.Position;

public interface OrderSourceDescription extends Position {

    OrderSource getSource();

    String getLabel();

    void setLabel(String label);

    String getDescription();

    void setDescription(String description);
}
