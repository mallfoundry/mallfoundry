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

package org.mallfoundry.marketing.coupon.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.data.repository.jpa.convert.StringListConverter;
import org.mallfoundry.marketing.coupon.Coupon;
import org.mallfoundry.marketing.coupon.CouponStatus;
import org.mallfoundry.marketing.coupon.CouponSupport;
import org.mallfoundry.marketing.coupon.CouponType;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_marketing_coupon")
public class JpaCoupon extends CouponSupport {

    @NotBlank
    @Id
    @Column(name = "id_")
    private String id;

    @NotBlank
    @Column(name = "tenant_id_")
    private String tenantId;

    @NotBlank
    @Column(name = "store_id_")
    private String storeId;

    //    @NotBlank
    @Column(name = "code_")
    private String code;

    @NotBlank
    @Column(name = "name_")
    private String name;

    @NotBlank
    @Column(name = "description_")
    private String description;

    @NotNull
    @Column(name = "status_")
    private CouponStatus status;

    @Min(0)
    @Column(name = "used_count_")
    private int usedCount;

    @Min(0)
    @Column(name = "received_count_")
    private int receivedCount;

    @Min(1)
    @Column(name = "issuing_count_")
    private int issuingCount;

    @Min(0)
    @Column(name = "take_limit_per_customer_")
    private int takeLimitPerCustomer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_")
    private CouponType type;

    @Column(name = "discount_amount_")
    private BigDecimal discountAmount;

    @Column(name = "discount_percent_")
    private BigDecimal discountPercent;

    @Column(name = "discount_min_amount_")
    private BigDecimal discountMinAmount;

    @Column(name = "discount_max_amount_")
    private BigDecimal discountMaxAmount;

    @Column(name = "min_amount_")
    private BigDecimal minAmount;

    @Column(name = "max_amount_")
    private BigDecimal maxAmount;

    @Convert(converter = StringListConverter.class)
    @Column(name = "products_")
    private List<String> products = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "excluded_products_")
    private List<String> excludedProducts = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "collections_")
    private List<String> collections = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "excluded_collections_")
    private List<String> excludedCollections = new ArrayList<>();

    @Column(name = "start_time_")
    private Date startTime;

    @Column(name = "end_time_")
    private Date endTime;

    @NotNull
    @Column(name = "created_time_")
    private Date createdTime;

    public JpaCoupon(String couponId) {
        this.id = couponId;
    }

    // takings
    public static JpaCoupon of(Coupon coupon) {
        if (coupon instanceof JpaCoupon) {
            return (JpaCoupon) coupon;
        }
        var target = new JpaCoupon();
        BeanUtils.copyProperties(coupon, target);
        return target;
    }
}
