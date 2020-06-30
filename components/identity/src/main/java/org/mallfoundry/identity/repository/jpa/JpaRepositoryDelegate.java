package org.mallfoundry.identity.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRepositoryDelegate extends JpaRepository<JpaUser, String> {

    Optional<JpaUser> findByMobile(String mobile);

    Optional<JpaUser> findByUsername(String username);
}

