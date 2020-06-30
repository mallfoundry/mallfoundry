package org.mallfoundry.identity;

public class ImmutableUserDeletedEvent extends UserEventSupport implements UserChangedEvent {

    public ImmutableUserDeletedEvent(User user) {
        super(user);
    }
}
