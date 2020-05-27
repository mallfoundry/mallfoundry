package org.mallfoundry.identity;

public class InternalUserDeletedEvent extends UserEventSupport implements UserChangedEvent {

    public InternalUserDeletedEvent(User user) {
        super(user);
    }
}
