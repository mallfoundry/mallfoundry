package org.mallfoundry.rest.catalog;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mallfoundry.catalog.BrandService;
import org.mallfoundry.rest.data.SliceListResponse;
import org.springframework.validation.annotation.Validated;
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

@Validated
@Tag(name = "Brand Resource V1", description = "商品品牌资源")
@RestController
@RequestMapping("/v1")
public class BrandResourceV1 {

    private final BrandService brandService;

    public BrandResourceV1(BrandService brandService) {
        this.brandService = brandService;
    }

    @Operation(summary = "添加一个商品品牌")
    @PostMapping("/brands")
    public BrandResponse addBrand(@RequestBody BrandRequest request) {
        return new BrandResponse(this.brandService.addBrand(request.assignToBrand(this.brandService.createBrand(null))));
    }

    @Operation(summary = "获得商品品牌分页集合")
    @GetMapping("/brands")
    public SliceListResponse<BrandResponse> getBrands(@Parameter(description = "页数")
                                                      @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                      @Parameter(schema = @Schema(maximum = "200", title = "limit2"))
                                                      @RequestParam(name = "limit", defaultValue = "100") Integer limit,
                                                      @RequestParam(name = "categories", required = false) Set<String> categories) {
        return SliceListResponse.of(
                this.brandService.getBrands(
                        this.brandService.createBrandQuery().toBuilder().page(page).limit(limit).categories(categories).build())
                        .map(BrandResponse::new));
    }

    @Operation(summary = "根据标识修改商品品牌")
    @PatchMapping("/brands/{brand_id}")
    public BrandResponse saveBrand(@PathVariable("brand_id") String brandId, @RequestBody BrandRequest request) {
        return new BrandResponse(this.brandService.saveBrand(request.assignToBrand(this.brandService.getBrand(brandId).orElseThrow())));
    }

    @Operation(summary = "根据标识删除商品品牌")
    @DeleteMapping("/brands/{brand_id}")
    public void deleteBrand(@PathVariable("brand_id") String brandId) {
        this.brandService.deleteBrand(brandId);
    }

}
