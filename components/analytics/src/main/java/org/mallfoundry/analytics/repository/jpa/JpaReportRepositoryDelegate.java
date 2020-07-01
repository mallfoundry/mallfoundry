package org.mallfoundry.analytics.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReportRepositoryDelegate extends JpaRepository<JpaReport, String> {
}
