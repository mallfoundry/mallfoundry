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

import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public class DefaultProductSalesService implements ProductSalesService {

    private final ProductService productService;

    private final ProductSalesRepository productSalesRepository;

    public DefaultProductSalesService(ProductService productService,
                                      ProductSalesRepository productSalesRepository) {
        this.productService = productService;
        this.productSalesRepository = productSalesRepository;
    }

    public ProductSalesQuery createProductSalesQuery() {
        return new DefaultProductSalesQuery();
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
        sales = this.productSalesRepository.save(sales);
        this.updateProductSales(sales);
        return sales;
    }

    private void updateProductSales(ProductSales sales) {
        var product = this.productService.createProduct(sales.getProductId());
        this.updateProductMonthlySales(product);
        this.updateProductTotalSales(product);
        this.productService.updateProductSales(product);
    }

    private void updateProductMonthlySales(Product product) {
        var endDate = LocalDate.now();
        var startDate = endDate.minusDays(30);
        // start date
        var yearStart = (short) startDate.getYear();
        var monthStart = (byte) startDate.getMonthValue();
        var dayOfMonthStart = (byte) startDate.getDayOfMonth();
        // end date
        var yearEnd = (short) endDate.getYear();
        var monthEnd = (byte) endDate.getMonthValue();
        var dayOfMonthEnd = (byte) endDate.getDayOfMonth();
        // start -- end
        var query = this.createProductSalesQuery().toBuilder()
                .productId(product.getId())
                .yearStart(yearStart).monthStart(monthStart).dayOfMonthStart(dayOfMonthStart)
                .yearEnd(yearEnd).monthEnd(monthEnd).dayOfMonthEnd(dayOfMonthEnd)
                .build();
        var quantities = this.productSalesRepository.sumQuantities(query);
        product.setMonthlySales(quantities);
    }

    private void updateProductTotalSales(Product product) {
        var query = this.createProductSalesQuery().toBuilder().productId(product.getId()).build();
        var quantities = this.productSalesRepository.sumQuantities(query);
        product.setTotalSales(quantities);
    }
}
