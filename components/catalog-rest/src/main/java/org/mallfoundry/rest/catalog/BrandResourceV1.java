package org.mallfoundry.rest.catalog;

import org.mallfoundry.catalog.BrandService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class BrandResourceV1 {

    private final BrandService brandService;

    public BrandResourceV1(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/brands")
    public BrandResponse addBrand(@RequestBody BrandRequest request) {
        return new BrandResponse(
                this.brandService.addBrand(request.assignToBrand(this.brandService.createBrand(null))));
    }

    @PatchMapping("/brands/{brand_id}")
    public BrandResponse saveBrand(@PathVariable("brand_id") String brandId, @RequestBody BrandRequest request) {
        return new BrandResponse(
                this.brandService.saveBrand(request.assignToBrand(this.brandService.getBrand(brandId).orElseThrow())));
    }

    @PatchMapping("/brands/{brand_id}")
    public void deleteBrand(@PathVariable("brand_id") String brandId) {
        this.brandService.deleteBrand(brandId);
    }

}
