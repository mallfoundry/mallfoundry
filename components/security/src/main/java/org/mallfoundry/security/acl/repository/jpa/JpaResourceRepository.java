package org.mallfoundry.security.acl.repository.jpa;

import org.mallfoundry.security.acl.InternalResource;
import org.mallfoundry.security.acl.ResourceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("jpaAclResourceRepository")
public interface JpaResourceRepository extends
        JpaRepository<InternalResource, String>, ResourceRepository {
}
