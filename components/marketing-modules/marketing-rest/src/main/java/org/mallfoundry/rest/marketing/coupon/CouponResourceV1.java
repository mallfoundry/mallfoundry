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

package org.mallfoundry.rest.marketing.coupon;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.marketing.coupon.Coupon;
import org.mallfoundry.marketing.coupon.CouponService;
import org.mallfoundry.marketing.coupon.CouponStatus;
import org.mallfoundry.marketing.coupon.CouponType;
import org.mallfoundry.marketing.coupon.TakeCoupon;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@Tag(name = "Coupons")
@RestController
@RequestMapping("/v1")
public class CouponResourceV1 {

    private final CouponService couponService;

    public CouponResourceV1(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/coupons")
    public Coupon addCoupon(@RequestBody CouponRequest request) {
        var coupon = request.assignTo(this.couponService.createCoupon(null));
        return this.couponService.addCoupon(coupon);
    }

    @GetMapping("/coupons/{coupon_id}")
    public Coupon getCoupon(@PathVariable(name = "coupon_id") String couponId) {
        return this.couponService.getCoupon(couponId);
    }

    @GetMapping("/coupons")
    public SliceList<Coupon> getCoupons(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                        @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                        @RequestParam(name = "tenant_id", required = false) String tenantId,
                                        @RequestParam(name = "store_id", required = false) String storeId,
                                        @RequestParam(name = "name", required = false) String name,
                                        @RequestParam(name = "types", required = false) Set<String> types,
                                        @RequestParam(name = "statuses", required = false) Set<String> statuses) {
        return this.couponService.getCoupons(
                this.couponService.createCouponQuery().toBuilder()
                        .page(page).limit(limit)
                        .tenantId(tenantId).storeId(storeId)
                        .name(name)
                        .types(() ->
                                CollectionUtils.emptyIfNull(types).stream().map(StringUtils::upperCase)
                                        .map(CouponType::valueOf).collect(Collectors.toUnmodifiableSet()))
                        .statuses(() ->
                                CollectionUtils.emptyIfNull(statuses).stream().map(StringUtils::upperCase)
                                        .map(CouponStatus::valueOf).collect(Collectors.toUnmodifiableSet()))
                        .build());
    }

    @PatchMapping("/coupons/{coupon_id}")
    public Coupon updateCoupon(@PathVariable(name = "coupon_id") String couponId,
                               @RequestBody CouponRequest request) {
        var coupon = request.assignTo(this.couponService.createCoupon(couponId));
        return this.couponService.updateCoupon(coupon);
    }

    @PatchMapping("/coupons/{coupon_id}/pause")
    public void pauseCoupon(@PathVariable(name = "coupon_id") String couponId) {
        this.couponService.pauseCoupon(couponId);
    }

    @DeleteMapping("/coupons/{coupon_id}")
    public void deleteCoupon(@PathVariable("coupon_id") String couponId) {
        this.couponService.deleteCoupon(couponId);
    }

    @PostMapping("/coupons/{coupon_id}/take")
    public TakeCoupon takeCoupon(@RequestBody CouponRequest request) {
        var coupon = request.assignTo(this.couponService.createCoupon(null));
        return this.couponService.takeCoupon(null);
    }

    @GetMapping("/customers/coupons")
    public SliceList<TakeCoupon> getTakeCoupons(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                                @RequestParam(name = "tenant_id", required = false) String tenantId,
                                                @RequestParam(name = "store_id", required = false) String storeId,
                                                @RequestParam(name = "customer_id", required = false) String customerId) {
        return this.couponService.getTakeCoupons(
                this.couponService.createCouponQuery().toBuilder()
                        .page(page).limit(limit)
                        .tenantId(tenantId).storeId(storeId).customerId(customerId)
                        .build());
    }
}
