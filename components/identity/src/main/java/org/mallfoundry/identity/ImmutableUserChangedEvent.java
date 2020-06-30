package org.mallfoundry.identity;

public class ImmutableUserChangedEvent extends UserEventSupport implements UserChangedEvent {

    public ImmutableUserChangedEvent(User user) {
        super(user);
    }
}
