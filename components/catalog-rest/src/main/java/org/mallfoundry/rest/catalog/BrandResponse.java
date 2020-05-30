package org.mallfoundry.rest.catalog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.mallfoundry.catalog.Brand;

import java.util.Collections;

@Getter
public class BrandResponse extends BrandRequest {

    @Schema(title = "标识")
    protected String id;

    @Schema(title = "排序")
    protected int position;

    public BrandResponse(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.description = brand.getDescription();
        this.logoUrl = brand.getLogoUrl();
        this.categories = Collections.unmodifiableSet(this.categories);
        this.searchKeywords = Collections.unmodifiableSet(this.categories);
        this.position = brand.getPosition();
    }
}
