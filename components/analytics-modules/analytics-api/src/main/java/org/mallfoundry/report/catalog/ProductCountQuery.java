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

package org.mallfoundry.report.catalog;

import org.mallfoundry.util.ObjectBuilder;

public interface ProductCountQuery extends ObjectBuilder.ToBuilder<ProductCountQuery.Builder> {

    String getTenantId();

    String getStoreId();

    String getCustomerId();

    Short getYear();

    Byte getMonth();

    Integer getDate();

    Integer getDateFrom();

    Integer getDateTo();

    interface Builder extends ObjectBuilder<ProductCountQuery> {

        Builder tenantId(String tenantId);

        Builder storeId(String storeId);

        Builder customerId(String customerId);

        Builder year(Short year);

        Builder month(Byte month);

        Builder date(Integer date);

        Builder dateFrom(Integer dateFrom);

        Builder dateTo(Integer dateTo);
    }
}
