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

package org.mallfoundry.rest.catalog;

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
@Tag(name = "Brands")
@RestController
@RequestMapping("/v1")
public class BrandResourceV1 {

    private final BrandService brandService;

    public BrandResourceV1(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/brands")
    public BrandResponse addBrand(@RequestBody BrandRequest request) {
        return new BrandResponse(this.brandService.addBrand(request.assignToBrand(this.brandService.createBrand(null))));
    }

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

    @PatchMapping("/brands/{brand_id}")
    public BrandResponse updateBrand(@PathVariable("brand_id") String brandId, @RequestBody BrandRequest request) {
        return new BrandResponse(this.brandService.updateBrand(request.assignToBrand(this.brandService.getBrand(brandId).orElseThrow())));
    }

    @DeleteMapping("/brands/{brand_id}")
    public void deleteBrand(@PathVariable("brand_id") String brandId) {
        this.brandService.deleteBrand(brandId);
    }

}
