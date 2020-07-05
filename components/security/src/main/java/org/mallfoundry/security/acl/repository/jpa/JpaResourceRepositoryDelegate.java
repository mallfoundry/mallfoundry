package org.mallfoundry.security.acl.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaResourceRepositoryDelegate extends JpaRepository<JpaResource, String> {

    Optional<JpaResource> findByTypeAndIdentifier(String type, String identifier);
}
