package org.mallfoundry.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InternalSearchTermId)) {
            return false;
        }
        InternalSearchTermId that = (InternalSearchTermId) o;
        return Objects.equals(customerId, that.customerId)
                && Objects.equals(term, that.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, term);
    }
}
