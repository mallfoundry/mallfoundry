package org.mallfoundry.analytics.report;

import org.mallfoundry.analytics.report.Report;

import java.util.Optional;

public interface ReportRepository {

    Report create(String id);

    Report save(Report report);

    Optional<Report> findById(String id);

    void delete(Report report);
}
