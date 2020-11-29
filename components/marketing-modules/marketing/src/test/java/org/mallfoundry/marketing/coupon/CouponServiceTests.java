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

package org.mallfoundry.marketing.coupon;

import org.junit.jupiter.api.Test;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@StandaloneTest
public class CouponServiceTests {

    @Autowired
    private CouponService couponService;

    @Test
    public void testAddCoupon() {
        var coupon = this.couponService.createCoupon(null);
        coupon.setCode("XXXX0000");
        coupon.setTenantId("0");
        coupon.setStoreId("mi");
        coupon.setName("满减优惠券");
        coupon.setDescription("描述");
        coupon.setType(CouponType.FIXED_DISCOUNT);
//        coupon.setTakeLimit(TakeLimit.ONCE_PER_CUSTOMER);
        coupon.setDiscountAmount(BigDecimal.valueOf(102));
        coupon.setIssuingCount(1100);
        this.couponService.addCoupon(coupon);
    }

    @Test
    public void testAddPercentageCoupon() {
        var coupon = this.couponService.createCoupon(null);
        coupon.setCode("XXXX0000");
        coupon.setTenantId("0");
        coupon.setStoreId("mi");
        coupon.setName("9.8折");
        coupon.setDescription("描述");
        coupon.setType(CouponType.PERCENTAGE_DISCOUNT);
        coupon.setDiscountPercent(BigDecimal.valueOf(9.8));
        coupon.setIssuingCount(1100);
        this.couponService.addCoupon(coupon);
    }

    @Test
    public void testTakeCoupon() {
        var coupon = this.couponService.createTakeCoupon(null);
        coupon.setCustomerId("1");
        coupon.setCouponId("6");
        this.couponService.takeCoupon(coupon);
    }

    @Test
    public void testTakeCoupon2() {
        var coupon = this.couponService.createTakeCoupon(null);
        coupon.setCustomerId("1");
        coupon.setCouponId("7");
        this.couponService.takeCoupon(coupon);
    }


    @Test
    public void testDeleteCoupon() {
        this.couponService.deleteCoupon("1");
    }
}
