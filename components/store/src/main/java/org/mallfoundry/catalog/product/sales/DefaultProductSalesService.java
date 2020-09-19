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

package org.mallfoundry.catalog.product.sales;

import org.mallfoundry.catalog.product.ProductService;
import org.springframework.transaction.annotation.Transactional;

public class DefaultProductSalesService implements ProductSalesService {

    private final ProductService productService;

    private final ProductSalesRepository productSalesRepository;

    public DefaultProductSalesService(ProductService productService,
                                      ProductSalesRepository productSalesRepository) {
        this.productService = productService;
        this.productSalesRepository = productSalesRepository;
    }

    @Override
    public ProductSales createProductSales() {
        return this.productSalesRepository.create();
    }

    private ProductSales getProductSales(ProductSales sales) {
        return this.productSalesRepository.findById(sales.toId())
                .orElseGet(this::createProductSales);
    }

    @Transactional
    @Override
    public ProductSales adjustProductSales(ProductSales source) {
        var sales = getProductSales(source);
        sales.adjustAmounts(source.getAmounts());
        sales.adjustQuantities(source.getQuantities());
        return this.productSalesRepository.save(sales);
    }
}
