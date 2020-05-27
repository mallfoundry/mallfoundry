package org.mallfoundry.identity;

import org.mallfoundry.util.ObjectEvent;

public interface UserEvent extends ObjectEvent {
    User getUser();
}
