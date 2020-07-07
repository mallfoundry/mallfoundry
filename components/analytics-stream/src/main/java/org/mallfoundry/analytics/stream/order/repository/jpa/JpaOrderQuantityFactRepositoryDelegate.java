package org.mallfoundry.analytics.stream.order.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderQuantityFactRepositoryDelegate extends JpaRepository<JpaOrderQuantityFact, String> {

}
