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

package org.mallfoundry.catalog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OptionSelectionTests {


    @Test
    public void testEquals() {
        var s1 = new DefaultOptionSelection("1", "1", "1", "1");
        var s2 = new DefaultOptionSelection("2", "2", "2", "2");
        var s3 = new DefaultOptionSelection("3", "3", "3", "3");
        var s4 = new DefaultOptionSelection("1", "1", "1", "1");
        var s5 = new DefaultOptionSelection("2", "2", "2", "2");
        var s6 = new DefaultOptionSelection("3", "3", "3", "3");
        var equals = OptionSelections.equals(List.of(s1, s2, s3), List.of(s4, s5, s6));
        Assertions.assertTrue(equals);
    }

}
