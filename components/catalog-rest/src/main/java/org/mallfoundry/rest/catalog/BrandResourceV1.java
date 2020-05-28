package org.mallfoundry.rest.catalog;

import org.mallfoundry.catalog.Brand;
import org.mallfoundry.catalog.BrandService;
import org.mallfoundry.data.SliceList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

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

    @GetMapping("/brands")
    public SliceList<Brand> getBrands(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                      @RequestParam(name = "limit", defaultValue = "100") Integer limit,
                                      @RequestParam(name = "categories", required = false) Set<String> categories) {
        return this.brandService.getBrands(
                this.brandService.createBrandQuery().toBuilder().page(page).limit(limit).categories(categories).build());
    }

    @PatchMapping("/brands/{brand_id}")
    public BrandResponse saveBrand(@PathVariable("brand_id") String brandId, @RequestBody BrandRequest request) {
        return new BrandResponse(
                this.brandService.saveBrand(request.assignToBrand(this.brandService.getBrand(brandId).orElseThrow())));
    }

    @DeleteMapping("/brands/{brand_id}")
    public void deleteBrand(@PathVariable("brand_id") String brandId) {
        this.brandService.deleteBrand(brandId);
    }

}
