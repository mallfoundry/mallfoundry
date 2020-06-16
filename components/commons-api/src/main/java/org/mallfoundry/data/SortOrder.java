package org.mallfoundry.data;

import java.io.Serializable;

public interface SortOrder extends Serializable {

    String getProperty();

    Direction getDirection();
}
