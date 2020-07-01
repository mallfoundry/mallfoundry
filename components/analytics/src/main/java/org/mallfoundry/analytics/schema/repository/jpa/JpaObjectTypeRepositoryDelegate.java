package org.mallfoundry.analytics.schema.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaObjectTypeRepositoryDelegate extends JpaRepository<JpaObjectType, String> {
}
