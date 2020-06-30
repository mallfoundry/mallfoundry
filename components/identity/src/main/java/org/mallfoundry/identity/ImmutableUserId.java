package org.mallfoundry.identity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImmutableUserId implements UserId {

    private String identifier;

    public ImmutableUserId(String identifier) {
        this.identifier = identifier;
    }
}
