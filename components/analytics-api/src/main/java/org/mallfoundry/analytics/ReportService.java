package org.mallfoundry.analytics;

import java.util.Optional;

public interface ReportService {

    Report createReport(String id);

    ReportQuery createReportQuery(String reportId);

    Report saveReport(Report report);

    Optional<Report> getReport(String id);

    QueryResult queryReport(ReportQuery query);

    void deleteReport(String id);
}
