/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
