package org.mallfoundry.customer.repository.jpa;

import org.mallfoundry.customer.InternalSearchTerm;
import org.mallfoundry.customer.InternalSearchTermId;
import org.mallfoundry.customer.SearchTermRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaSearchTermRepository extends
        JpaRepository<InternalSearchTerm, InternalSearchTermId>,
        SearchTermRepository {

    @Query("from InternalSearchTerm where customerId = ?1 order by timestamp desc")
    @Override
    List<InternalSearchTerm> findAllByCustomerId(String customerId);
}
