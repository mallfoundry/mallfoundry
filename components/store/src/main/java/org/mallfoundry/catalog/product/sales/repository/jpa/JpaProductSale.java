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
import org.mallfoundry.catalog.product.sales.ProductSale;
import org.mallfoundry.catalog.product.sales.ProductSaleId;
import org.mallfoundry.catalog.product.sales.ProductSaleSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_catalog_product_sale")
@IdClass(JpaProductSaleId.class)
public class JpaProductSale extends ProductSaleSupport {

    @Id
    @Column(name = "product_id_")
    private String productId;

    @Id
    @Column(name = "variant_id_")
    private String variantId;

    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "sold_date_")
    private Date soldDate;

    @Column(name = "total_amounts_")
    private BigDecimal totalAmounts = BigDecimal.ZERO;

    @Column(name = "total_quantities_")
    private long totalQuantities;

    public JpaProductSale(Long quantities) {
        this.totalQuantities = Objects.requireNonNullElse(quantities, (long) 0);
    }

    @Override
    public ProductSaleId toId() {
        return new JpaProductSaleId(this.productId, this.variantId, this.soldDate);
    }

    public static JpaProductSale of(ProductSale dailySales) {
        if (dailySales instanceof JpaProductSale) {
            return (JpaProductSale) dailySales;
        }
        var target = new JpaProductSale();
        BeanUtils.copyProperties(dailySales, target);
        return target;
    }
}
