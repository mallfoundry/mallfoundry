package org.mallfoundry.analytics.report;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultReportService implements ReportService {

    private final QueryExecutor queryExecutor;

    private final ReportRepository reportRepository;

    public DefaultReportService(QueryExecutor queryExecutor, ReportRepository reportRepository) {
        this.queryExecutor = queryExecutor;
        this.reportRepository = reportRepository;
    }

    @Override
    public Report createReport(String id) {
        return this.reportRepository.create(id);
    }

    @Override
    public ReportQuery createReportQuery(String reportId) {
        return new DefaultReportQuery(reportId);
    }

    @Override
    public Report saveReport(Report report) {
        return this.reportRepository.save(report);
    }

    @Override
    public Optional<Report> getReport(String id) {
        return this.reportRepository.findById(id);
    }

    @Override
    public QueryResult queryReport(ReportQuery query) {
        var report = this.reportRepository.findById(query.getReportId()).orElseThrow();
        return this.queryExecutor.execute(query, report);
    }

    @Override
    public void deleteReport(String id) {
        var report = this.reportRepository.findById(id).orElseThrow();
        this.reportRepository.delete(report);
    }
}
