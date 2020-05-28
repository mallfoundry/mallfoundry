package org.mallfoundry.customer;

import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InternalSearchTermService implements SearchTermService {

    private final SearchTermRepository searchTermRepository;

    public InternalSearchTermService(SearchTermRepository searchTermRepository) {
        this.searchTermRepository = searchTermRepository;
    }

    private void incrementTermHits(InternalSearchTerm term) {
        term.incrementHits();
        this.searchTermRepository.save(term);
    }

    private void addNewTerm(String customerId, String term) {
        this.searchTermRepository.save(new InternalSearchTerm(customerId, term));
    }

    @Transactional
    @Override
    public void addTerm(String customerId, String term) {
        this.searchTermRepository.findById(InternalSearchTermId.of(customerId, term))
                .ifPresentOrElse(this::incrementTermHits, () -> addNewTerm(customerId, term));
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
