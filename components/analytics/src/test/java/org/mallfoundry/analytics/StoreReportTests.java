package org.mallfoundry.analytics;

import org.junit.jupiter.api.Test;
import org.mallfoundry.analytics.store.StoreReportService;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;

@StandaloneTest
public class StoreReportTests {

    @Autowired
    private StoreReportService storeReportService;

    @Test
    public void testCountTotalOrderQuantity() {
        var storeId = "mi";
        var totalQuantity = this.storeReportService.countTotalOrderQuantity(storeId).orElse(null);
        System.out.println(totalQuantity);
    }
}
