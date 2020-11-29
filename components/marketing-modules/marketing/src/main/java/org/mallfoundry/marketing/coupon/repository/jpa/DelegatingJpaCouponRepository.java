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

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.marketing.coupon.Coupon;
import org.mallfoundry.marketing.coupon.CouponQuery;
import org.mallfoundry.marketing.coupon.CouponRepository;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class DelegatingJpaCouponRepository implements CouponRepository {

    private final JpaCouponRepository repository;

    public DelegatingJpaCouponRepository(JpaCouponRepository repository) {
        this.repository = repository;
    }

    @Override
    public Coupon create(String couponId) {
        return new JpaCoupon(couponId);
    }

    @Override
    public Coupon save(Coupon coupon) {
        return this.repository.save(JpaCoupon.of(coupon));
    }

    @Override
    public Optional<Coupon> findById(String couponId) {
        return CastUtils.cast(this.repository.findById(couponId));
    }

    @Override
    public SliceList<Coupon> findAll(CouponQuery query) {
        var page = this.repository.findAll(query);
        return PageList.of(page.getContent())
                .page(query.getPage()).limit(query.getLimit())
                .totalSize(page.getTotalElements())
                .cast();
    }

    @Override
    public void delete(Coupon coupon) {
        this.repository.delete(JpaCoupon.of(coupon));
    }
}
