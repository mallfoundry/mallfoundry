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
import org.mallfoundry.catalog.product.sales.ProductDailySales;
import org.mallfoundry.catalog.product.sales.ProductDailySalesId;
import org.mallfoundry.catalog.product.sales.ProductDailySalesSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_catalog_product_daily_sales")
public class JpaProductDailySales extends ProductDailySalesSupport {

    @Id
    @Column(name = "product_id_")
    private String productId;

    @Id
    @Column(name = "variant_id_")
    private String variantId;

    @Id
    @Column(name = "year_")
    private int year;

    @Id
    @Column(name = "month_")
    private int month;

    @Id
    @Column(name = "day_of_month_")
    private int dayOfMonth;

    @Column(name = "amounts_")
    private BigDecimal amounts = BigDecimal.ZERO;

    @Column(name = "quantities_")
    private int quantities;

    @Override
    public ProductDailySalesId toId() {
        return new JpaProductDailySalesId(this.productId, this.variantId, this.year, this.month, this.dayOfMonth);
    }

    public static JpaProductDailySales of(ProductDailySales dailySales) {
        if (dailySales instanceof JpaProductDailySales) {
            return (JpaProductDailySales) dailySales;
        }
        var target = new JpaProductDailySales();
        BeanUtils.copyProperties(dailySales, target);
        return target;
    }
}
