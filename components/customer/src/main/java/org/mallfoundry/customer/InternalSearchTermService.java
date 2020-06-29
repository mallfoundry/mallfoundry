package org.mallfoundry.customer;

import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@Service
public class InternalSearchTermService implements SearchTermService {

    private final SearchTermRepository searchTermRepository;

    public InternalSearchTermService(SearchTermRepository searchTermRepository) {
        this.searchTermRepository = searchTermRepository;
    }

    private Supplier<InternalSearchTerm> createNewSearchTerm(String customerId, String term) {
        return () -> new InternalSearchTerm(customerId, term);
    }

    @Transactional
    @Override
    public SearchTerm addSearchTerm(String customerId, String termText) {
        var term = this.searchTermRepository
                .findById(InternalSearchTermId.of(customerId, termText))
                .orElseGet(createNewSearchTerm(customerId, termText));
        term.hit();
        return this.searchTermRepository.save(term);
    }

    @Override
    public List<SearchTerm> getSearchTerms(String customerId) {
        return CastUtils.cast(this.searchTermRepository.findAllByCustomerId(customerId));
    }

    @Transactional
    @Override
    public void deleteSearchTerm(String customerId, String term) {
        this.searchTermRepository.deleteByCustomerIdAndTerm(customerId, term);
    }

    @Transactional
    @Override
    public void deleteSearchTerms(String customerId, List<String> terms) {
        this.searchTermRepository.deleteByCustomerIdAndTermIn(customerId, terms);
    }

    @Transactional
    @Override
    public void clearSearchTerms(String customerId) {
        this.searchTermRepository.deleteByCustomerId(customerId);
    }
}
