package org.mallfoundry.identity;

public class ImmutableUserCreatedEvent extends UserEventSupport implements UserCreatedEvent {
    public ImmutableUserCreatedEvent(User user) {
        super(user);
    }
}
