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

package org.mallfoundry.catalog.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
public class DefaultProductAttribute extends ProductAttributeSupport {

    private String namespace;

    private String name;

    private String value;

    private int position;

    public DefaultProductAttribute(String name, String value) {
        super(name, value);
    }

    public DefaultProductAttribute(String namespace, String name, String value) {
        super(namespace, name, value);
    }

    public static DefaultProductAttribute of(ProductAttribute attribute) {
        if (attribute instanceof DefaultProductAttribute) {
            return (DefaultProductAttribute) attribute;
        }
        var target = new DefaultProductAttribute();
        BeanUtils.copyProperties(attribute, target);
        return target;
    }
}