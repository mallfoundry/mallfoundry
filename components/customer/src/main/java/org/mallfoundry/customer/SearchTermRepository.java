package org.mallfoundry.customer;

import java.util.List;
import java.util.Optional;

public interface SearchTermRepository {

    Optional<InternalSearchTerm> findById(InternalSearchTermId internalSearchTermId);

    List<InternalSearchTerm> findAllByCustomerId(String customerId);

    <S extends InternalSearchTerm> S save(S entity);

    void delete(InternalSearchTerm entity);
}
