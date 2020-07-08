package org.mallfoundry.rest.analytics.store;

import org.mallfoundry.analytics.store.StoreReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/stores")
public class StoreReportResourceV1 {

    private final StoreReportService storeReportService;

    public StoreReportResourceV1(StoreReportService storeReportService) {
        this.storeReportService = storeReportService;
    }

    @GetMapping("/{store_id}/reports/total_order_quantity")
    public Optional<StoreTotalOrderQuantityResponse> countTotalOrderQuantity(@PathVariable("store_id") String storeId) {
        return this.storeReportService
                .countTotalOrderQuantity(storeId)
                .map(StoreTotalOrderQuantityResponse::of);
    }

    @GetMapping("/{store_id}/reports/total_product_quantity")
    public Optional<StoreTotalProductQuantityResponse> countTotalProductQuantity(@PathVariable("store_id") String storeId) {
        return this.storeReportService
                .countTotalProductQuantity(storeId)
                .map(StoreTotalProductQuantityResponse::of);
    }
}
