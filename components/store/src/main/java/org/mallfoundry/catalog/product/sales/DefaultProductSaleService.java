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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultProductSaleService implements ProductSaleService {

    private final ProductService productService;

    private final ProductSaleRepository productSalesRepository;

    public DefaultProductSaleService(ProductService productService,
                                     ProductSaleRepository productSalesRepository) {
        this.productService = productService;
        this.productSalesRepository = productSalesRepository;
    }

    public ProductSaleQuery createProductSalesQuery() {
        return new DefaultProductSaleQuery();
    }

    @Override
    public ProductSale createProductSale() {
        return this.productSalesRepository.create();
    }

    private ProductSale getProductSale(ProductSale sales) {
        return this.productSalesRepository.findById(sales.toId())
                .orElseGet(this::createProductSale);
    }

    @Transactional
    @Override
    public ProductSale adjustProductSale(ProductSale source) {
        var sales = getProductSale(source);
        sales.adjustTotalAmounts(source.getTotalAmounts());
        sales.adjustTotalQuantities(source.getTotalQuantities());
        sales = this.productSalesRepository.save(sales);
        this.updateProductSales(sales);
        return sales;
    }

    @Transactional
    @Override
    public List<ProductSale> adjustProductSales(Collection<ProductSale> sales) {
        return sales.stream().map(this::adjustProductSale).collect(Collectors.toUnmodifiableList());
    }

    private void updateProductSales(ProductSale sales) {
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
