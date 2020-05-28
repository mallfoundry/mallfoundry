package org.mallfoundry.rest.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.customer.SearchTerm;

@Getter
@Setter
@NoArgsConstructor
public class SearchTermResponse {

    private String term;

    private long timestamp;

    private int hits;

    public SearchTermResponse(SearchTerm term) {
        this.term = term.getTerm();
        this.timestamp = term.getTimestamp();
        this.hits = term.getHits();
    }
}
