package org.mallfoundry.customer;

import java.util.List;

public interface SearchTermService {

    SearchTerm addSearchTerm(String customerId, String term);

    List<SearchTerm> getSearchTerms(String customerId);

    void deleteSearchTerm(String customerId, String term);

    void deleteSearchTerms(String customerId, List<String> terms);

    void clearSearchTerms(String customerId);
}
