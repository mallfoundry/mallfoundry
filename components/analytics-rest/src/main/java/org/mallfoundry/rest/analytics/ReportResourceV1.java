package org.mallfoundry.rest.analytics;

import org.mallfoundry.analytics.QueryResult;
import org.mallfoundry.analytics.Report;
import org.mallfoundry.analytics.ReportService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class ReportResourceV1 {

    private final ReportService reportService;

    public ReportResourceV1(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports/{id}")
    public Optional<Report> getReport(@PathVariable("id") String id) {
        return this.reportService.getReport(id);
    }

    @GetMapping("/reports/{id}/query")
    public QueryResult queryReport(@PathVariable("id") String reportId,
                                   @RequestParam(name = "result_type", required = false) String resultType,
                                   @RequestParam Map<String, Object> parameters) {
        var query = this.reportService.createReportQuery(reportId)
                .toBuilder().resultType(resultType).parameters(parameters).build();
        return this.reportService.queryReport(query);
    }

    @DeleteMapping("/reports/{id}")
    public void deleteReport(@PathVariable("id") String id) {
        this.reportService.deleteReport(id);
    }
}
