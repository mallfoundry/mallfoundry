package org.mallfoundry.rest.catalog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.catalog.Brand;

import java.util.Set;

@Schema(title = "商品品牌")
@Getter
@Setter
public class BrandRequest {

    @Schema(title = "品牌名称")
    private String name;

    @Schema(title = "品牌描述")
    private String description;

    @Schema(name = "logo_url", title = "品牌LOGO URL")
    private String logoUrl;

    @Schema(title = "品牌关联的商品类目集合")
    private Set<String> categories;

    @Schema(name = "search_keywords", title = "品牌用于搜索的关键字集合")
    private Set<String> searchKeywords;

    public Brand assignToBrand(Brand brand) {
        return brand.toBuilder()
                .name(this.name)
                .description(this.description)
                .logoUrl(this.logoUrl)
                .categories(this.categories)
                .searchKeywords(this.searchKeywords)
                .build();
    }

}
