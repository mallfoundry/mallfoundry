package org.mallfoundry.analytics;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReportTests {

    @Autowired
    private ReportService reportService;

    @Test
    public void testCreateReport() {
        var report = this.reportService.createReport("order_sales")
                .toBuilder()
                .name("Order Sales")
                .statement("SELECT id_ FROM mf_catalog_product where store_id_ = :store_id")
                .statementType(ReportStatementType.SQL)
                .build();
        var savedReport = this.reportService.saveReport(report);
        System.out.println(savedReport);
    }

    @Test
    public void testGetReport() {

    }

    @Test
    public void testQueryReport() {
        var query = this.reportService.createReportQuery("order_sales").toBuilder().parameter("store_id", "mi").build();
        Object object = this.reportService.queryReport(query);
        System.out.println(object);
    }
}
