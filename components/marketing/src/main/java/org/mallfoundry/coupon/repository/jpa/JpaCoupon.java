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

package org.mallfoundry.coupon.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.coupon.ApplyLimit;
import org.mallfoundry.coupon.Coupon;
import org.mallfoundry.coupon.CouponId;
import org.mallfoundry.coupon.CouponStatus;
import org.mallfoundry.coupon.CouponSupport;
import org.mallfoundry.coupon.CouponType;
import org.mallfoundry.data.repository.jpa.convert.StringListConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_coupon")
public class JpaCoupon extends CouponSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "tenant_id_")
    private String tenantId;

    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "code_")
    private String code;

    @Column(name = "name_")
    private String name;

    @Column(name = "description_")
    private String description;

    @Column(name = "type_")
    private CouponType type;

    @Column(name = "status_")
    private CouponStatus status;

    @Column(name = "apply_limit_")
    private ApplyLimit applyLimit;

    @Column(name = "uses_")
    private int uses;

    @Column(name = "max_uses_")
    private int maxUses;

    @Column(name = "max_uses_per_customer_")
    private int maxUsesPerCustomer;

    @Column(name = "expires_")
    private int expires;

    @Column(name = "amount_")
    private BigDecimal amount;

    @Column(name = "min_amount_")
    private BigDecimal minAmount;

    @Column(name = "max_amount_")
    private BigDecimal maxAmount;

    @Convert(converter = StringListConverter.class)
    @Column(name = "products_")
    private List<String> products;

    @Convert(converter = StringListConverter.class)
    @Column(name = "excluded_products_")
    private List<String> excludedProducts;

    @Convert(converter = StringListConverter.class)
    @Column(name = "collections_")
    private List<String> collections;

    @Convert(converter = StringListConverter.class)
    @Column(name = "excluded_collections_")
    private List<String> excludedCollections;

    @Column(name = "created_time_")
    private Date createdTime;

    public JpaCoupon(CouponId couponId) {
        this.id = couponId.getId();
        this.tenantId = couponId.getTenantId();
    }

    public static JpaCoupon of(Coupon coupon) {
        if (coupon instanceof JpaCoupon) {
            return (JpaCoupon) coupon;
        }
        var target = new JpaCoupon();
        BeanUtils.copyProperties(coupon, target);
        return target;
    }
}
