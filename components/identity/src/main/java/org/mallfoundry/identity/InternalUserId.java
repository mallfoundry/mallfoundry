package org.mallfoundry.identity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalUserId implements UserId {

    private String identifier;

    public InternalUserId(String identifier) {
        this.identifier = identifier;
    }
}
