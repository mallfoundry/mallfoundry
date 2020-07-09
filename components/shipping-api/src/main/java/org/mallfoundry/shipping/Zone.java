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

package org.mallfoundry.shipping;

import java.math.BigDecimal;
import java.util.List;

public interface Zone {

    String getId();

    String getName();

    void setName(String name);

    List<String> getLocations();

    void setLocations(List<String> locations);

    BigDecimal getFirstCost();

    void setFirstCost(BigDecimal cost);

    BigDecimal getFirst();

    void setFirst(BigDecimal amount);

    BigDecimal getAdditionalCost();

    void setAdditionalCost(BigDecimal cost);

    BigDecimal getAdditional();

    void setAdditional(BigDecimal amount);
}
