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

package org.mallfoundry.report.catalog.repository.mybatis;

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.report.catalog.ProductSale;
import org.mallfoundry.report.catalog.ProductSalesQuery;
import org.mallfoundry.report.catalog.ProductSalesReportRepository;

public class DelegatingMybatisProductSalesReportRepository implements ProductSalesReportRepository {

    private final MybatisProductSalesReportRepository repository;

    public DelegatingMybatisProductSalesReportRepository(MybatisProductSalesReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public SliceList<ProductSale> queryProductSales(ProductSalesQuery query) {
        return PageList.of(this.repository.queryProductSales(query));
    }
}
