package org.mallfoundry.customer.repository.jpa;

import org.mallfoundry.customer.InternalSearchTerm;
import org.mallfoundry.customer.InternalSearchTermId;
import org.mallfoundry.customer.SearchTermRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSearchTermRepository extends
        JpaRepository<InternalSearchTerm, InternalSearchTermId>,
        SearchTermRepository {
}
