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

    private Supplier<InternalSearchTerm> createNewTerm(String customerId, String term) {
        return () -> new InternalSearchTerm(customerId, term);
    }

    @Transactional
    @Override
    public SearchTerm addTerm(String customerId, String termText) {
        var term = this.searchTermRepository
                .findById(InternalSearchTermId.of(customerId, termText))
                .orElseGet(createNewTerm(customerId, termText));
        term.hit();
        return this.searchTermRepository.save(term);
    }

    @Override
    public List<SearchTerm> getTerms(String customerId) {
        return CastUtils.cast(this.searchTermRepository.findAllByCustomerId(customerId));
    }

    @Override
    public void deleteTerm(String customerId, String term) {

    }

    @Override
    public void deleteTerms(String customerId, List<String> terms) {

    }

    @Override
    public void clearTerms(String customerId) {

    }
}
