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
