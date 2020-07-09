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
    protected String name;

    @Schema(title = "品牌描述")
    protected String description;

    @Schema(name = "logo_url", title = "品牌LOGO URL")
    protected String logoUrl;

    @Schema(title = "品牌关联的商品类目集合")
    protected Set<String> categories;

    @Schema(name = "search_keywords", title = "品牌用于搜索的关键字集合")
    protected Set<String> searchKeywords;

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
