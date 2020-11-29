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

import org.junit.jupiter.api.Test;
import org.mallfoundry.catalog.product.repository.jpa.JpaProductOption;

import java.util.List;
import java.util.stream.Collectors;

public class ProductOptionTests {

    @Test
    public void testSubtract() {
        var o1 = new JpaProductOption().toBuilder().id("i1").name("n1").build();
        var o2 = new JpaProductOption().toBuilder().id("i2").name("n2").build();
        var o3 = new JpaProductOption().toBuilder().id("i3").name("n3").build();

        var opts1 = List.of(o1, o2, o3);

        var s1 = new JpaProductOption().toBuilder().name("n1").build();
        var s2 = new JpaProductOption().toBuilder().name("n2").build();
        var opts2 = List.of(s1, s2);

        var names = opts2.stream().map(ProductOption::getName).collect(Collectors.toUnmodifiableSet());
        var removedOpts = opts1.stream().filter(option -> !names.contains(option.getName())).collect(Collectors.toUnmodifiableSet());

    }
}
