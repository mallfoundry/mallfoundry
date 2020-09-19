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

    private final ProductDailySalesRepository productDailySalesRepository;

    public DefaultProductSalesService(ProductService productService,
                                      ProductDailySalesRepository productDailySalesRepository) {
        this.productService = productService;
        this.productDailySalesRepository = productDailySalesRepository;
    }

    @Override
    public ProductDailySales createProductDailySales() {
        return this.productDailySalesRepository.create();
    }

    private ProductDailySales getProductDailySales(ProductDailySales sales) {
        return this.productDailySalesRepository.findById(sales.toId())
                .orElseGet(this::createProductDailySales);
    }

    @Transactional
    @Override
    public ProductDailySales adjustProductDailySales(ProductDailySales source) {
        var sales = getProductDailySales(source);
        sales.adjustAmounts(source.getAmounts());
        sales.adjustQuantities(source.getQuantities());
        return this.productDailySalesRepository.save(sales);
    }
}
