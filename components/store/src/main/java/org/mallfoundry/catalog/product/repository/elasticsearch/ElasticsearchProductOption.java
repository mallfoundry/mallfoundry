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

package org.mallfoundry.catalog.product.repository.elasticsearch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.product.ProductOption;
import org.mallfoundry.catalog.product.ProductOptionSupport;
import org.mallfoundry.catalog.product.ProductOptionValue;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ElasticsearchProductOption extends ProductOptionSupport {

    private String id;

    private String name;

    private List<ProductOptionValue> values = new ArrayList<>();

    private int position;

    public ElasticsearchProductOption(String id) {
        super(id);
    }

    public static ElasticsearchProductOption of(ProductOption option) {
        if (option instanceof ElasticsearchProductOption) {
            return (ElasticsearchProductOption) option;
        }
        var target = new ElasticsearchProductOption();
        BeanUtils.copyProperties(option, target);
        return target;
    }
}
