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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
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


    public ProductSale createNewProductSale(ProductSale sale) {
        return this.productSalesRepository.create().toBuilder()
                .productId(sale.getProductId()).variantId(sale.getVariantId())
                .soldDate(sale.getSoldDate())
                .totalQuantities(0).totalAmounts(BigDecimal.ZERO)
                .build();
    }

    private ProductSale getProductSale(ProductSale sale) {
        return this.productSalesRepository.findById(sale.toId())
                .orElseGet(() -> this.createNewProductSale(sale));
    }

    @Transactional
    @Override
    public ProductSale adjustProductSale(ProductSale source) {
        var sale = getProductSale(source);
        sale.adjustTotalAmounts(source.getTotalAmounts());
        sale.adjustTotalQuantities(source.getTotalQuantities());
        sale = this.productSalesRepository.save(sale);
        this.updateProductSales(sale);
        return sale;
    }

    @Transactional
    @Override
    public List<ProductSale> adjustProductSales(Collection<ProductSale> sales) {
        return sales.stream().map(this::adjustProductSale).collect(Collectors.toUnmodifiableList());
    }

    private void updateProductSales(ProductSale sale) {
        var product = this.productService.createProduct(sale.getProductId());
        this.updateProductMonthlySales(product);
        this.updateProductTotalSales(product);
        this.productService.updateProductSales(product);
    }

    private void updateProductMonthlySales(Product product) {
        var endLocalDate = LocalDate.now();
        ZoneId zone = ZoneId.systemDefault();
        var endDate = Date.from(endLocalDate.atStartOfDay().atZone(zone).toInstant());
        var startLocalDate = endLocalDate.minusDays(30);
        var startDate = Date.from(startLocalDate.atStartOfDay().atZone(zone).toInstant());
        // start -- end
        var query = this.createProductSalesQuery().toBuilder()
                .productId(product.getId())
                .soldDateStart(startDate).soldDateEnd(endDate)
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
