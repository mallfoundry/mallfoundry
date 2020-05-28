package org.mallfoundry.customer;

import java.util.List;

public interface SearchTermService {

    void addTerm(String customerId, String term);

    List<SearchTerm> getTerms(String customerId);

    void deleteTerm(String customerId, String term);

    void deleteTerms(String customerId, List<String> terms);

    void clearTerms(String customerId);
}
