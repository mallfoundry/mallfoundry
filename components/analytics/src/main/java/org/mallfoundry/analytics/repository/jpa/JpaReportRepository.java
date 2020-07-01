package org.mallfoundry.analytics.repository.jpa;

import org.mallfoundry.analytics.Report;
import org.mallfoundry.analytics.ReportRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaReportRepository implements ReportRepository {

    private final JpaReportRepositoryDelegate reportRepository;

    public JpaReportRepository(JpaReportRepositoryDelegate reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report create(String id) {
        return new JpaReport(id);
    }

    @Override
    public Report save(Report report) {
        return this.reportRepository.save(JpaReport.of(report));
    }

    @Override
    public Optional<Report> findById(String id) {
        return CastUtils.cast(this.reportRepository.findById(id));
    }

    @Override
    public void delete(Report report) {
        this.reportRepository.delete(JpaReport.of(report));
    }
}
