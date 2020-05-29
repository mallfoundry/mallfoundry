package org.mallfoundry.catalog;

import org.mallfoundry.util.ObjectEvent;

public interface CategoryEvent extends ObjectEvent {
    Category getCategory();
}
