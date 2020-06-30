package org.mallfoundry.identity.repository.jpa;

import org.mallfoundry.identity.InternalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRepositoryDelegate extends JpaRepository<InternalUser, String> {

    Optional<InternalUser> findByMobile(String mobile);

    Optional<InternalUser> findByUsername(String username);
}

