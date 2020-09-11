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

import org.mallfoundry.data.SliceList;
import org.springframework.transaction.annotation.Transactional;

public class DefaultCouponService implements CouponService {

    private final CouponRepository couponRepository;

    public DefaultCouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public CouponId createCouponId(String couponId) {
        return new ImmutableCouponId(couponId);
    }

    @Override
    public Coupon createCoupon(CouponId couponId) {
        return this.couponRepository.create(couponId);
    }

    @Transactional
    @Override
    public Coupon addCoupon(Coupon coupon) {
        return this.couponRepository.save(coupon);
    }

    @Override
    public SliceList<Coupon> getCoupons(CouponQuery query) {
        return this.couponRepository.findAll(query);
    }

    private Coupon requiredCoupon(CouponId couponId) {
        return this.couponRepository.findById(couponId).orElseThrow();
    }

    @Transactional
    @Override
    public void updateCoupon(Coupon source) {
        var coupon = this.requiredCoupon(source.toId());
        this.couponRepository.save(coupon);
    }

    @Transactional
    @Override
    public void deleteCoupon(CouponId couponId) {
        var coupon = this.requiredCoupon(couponId);
        this.couponRepository.delete(coupon);
    }

    @Override
    public ReceiveCoupon createReceiveCoupon() {
        return null;
    }

    @Override
    public ReceiveCoupon receiveCoupon(ReceiveCoupon receiveCoupon) {
        return null;
    }

    @Override
    public SliceList<ReceiveCoupon> getReceiveCoupons(CouponQuery query) {
        return null;
    }
}
