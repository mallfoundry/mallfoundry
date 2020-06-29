package org.mallfoundry.app.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMenuRepositoryDelegate extends JpaRepository<JpaMenu, String> {
}
