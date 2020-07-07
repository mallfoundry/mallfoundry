package org.mallfoundry.analytics.report.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReportRepositoryDelegate extends JpaRepository<JpaReport, String> {
}
