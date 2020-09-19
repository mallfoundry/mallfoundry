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

package org.mallfoundry.catalog.product.sales.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.product.sales.ProductSales;
import org.mallfoundry.catalog.product.sales.ProductSalesId;
import org.mallfoundry.catalog.product.sales.ProductSalesSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_catalog_product_sales")
@IdClass(JpaProductSalesId.class)
public class JpaProductSales extends ProductSalesSupport {

    @Id
    @Column(name = "product_id_")
    private String productId;

    @Id
    @Column(name = "variant_id_")
    private String variantId;

    @Id
    @Column(name = "year_")
    private short year;

    @Id
    @Column(name = "month_")
    private byte month;

    @Id
    @Column(name = "day_of_month_")
    private byte dayOfMonth;

    @Column(name = "amounts_")
    private BigDecimal amounts = BigDecimal.ZERO;

    @Column(name = "quantities_")
    private long quantities;

    public JpaProductSales(Long quantities) {
        this.quantities = Objects.requireNonNullElse(quantities, (long) 0);
    }

    @Override
    public ProductSalesId toId() {
        return new JpaProductSalesId(this.productId, this.variantId, this.year, this.month, this.dayOfMonth);
    }

    public static JpaProductSales of(ProductSales dailySales) {
        if (dailySales instanceof JpaProductSales) {
            return (JpaProductSales) dailySales;
        }
        var target = new JpaProductSales();
        BeanUtils.copyProperties(dailySales, target);
        return target;
    }
}
