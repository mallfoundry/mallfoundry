package org.mallfoundry.customer;

import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class InternalSearchTermService implements SearchTermService {

    private final SearchTermRepository searchTermRepository;

    public InternalSearchTermService(SearchTermRepository searchTermRepository) {
        this.searchTermRepository = searchTermRepository;
    }

    private InternalSearchTerm incrementTermHits(InternalSearchTerm term) {
        term.incrementHits();
        return this.searchTermRepository.save(term);
    }

    private InternalSearchTerm addNewTerm(String customerId, String term) {
        return this.searchTermRepository.save(new InternalSearchTerm(customerId, term));
    }

    @Transactional
    @Override
    public SearchTerm addTerm(String customerId, String termText) {
        var term = this.searchTermRepository.findById(InternalSearchTermId.of(customerId, termText)).orElse(null);
        return Objects.nonNull(term)
                ? incrementTermHits(term)
                : addNewTerm(customerId, termText);
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
