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

package org.mallfoundry.analytics.schema;

import org.junit.jupiter.api.Test;
import org.mallfoundry.analytics.schema.ObjectTypeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ObjectTypeTests {

    @Autowired
    private ObjectTypeManager objectTypeManager;

    @Test
    public void testAddProductType() {
        var productType = this.objectTypeManager.createObjectType("product")
                .toBuilder().name("Product Type")
                .addField(p -> p.createField("id").toBuilder().label("商品标识").type("String").build())
                .addField(p -> p.createField("name").toBuilder().label("商品名称").type("String").build())
                .build();
        objectTypeManager.addObjectType(productType);
    }
}
