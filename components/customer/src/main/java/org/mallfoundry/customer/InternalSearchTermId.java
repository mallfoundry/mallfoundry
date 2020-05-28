package org.mallfoundry.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class InternalSearchTermId implements Serializable {

    private String customerId;

    private String term;

    public InternalSearchTermId(String customerId, String term) {
        this.customerId = customerId;
        this.term = term;
    }

    public static InternalSearchTermId of(String customerId, String term) {
        return new InternalSearchTermId(customerId, term);
    }
}
