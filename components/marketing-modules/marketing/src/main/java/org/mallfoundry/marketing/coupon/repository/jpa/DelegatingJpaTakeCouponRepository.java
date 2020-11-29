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
import org.mallfoundry.marketing.coupon.CouponQuery;
import org.mallfoundry.marketing.coupon.TakeCoupon;
import org.mallfoundry.marketing.coupon.TakeCouponRepository;

public class DelegatingJpaTakeCouponRepository implements TakeCouponRepository {

    private final JpaTakeCouponRepository repository;

    public DelegatingJpaTakeCouponRepository(JpaTakeCouponRepository repository) {
        this.repository = repository;
    }

    @Override
    public TakeCoupon create(String id) {
        return new JpaTakeCoupon(id);
    }

    @Override
    public TakeCoupon save(TakeCoupon coupon) {
        return this.repository.save(JpaTakeCoupon.of(coupon));
    }

    @Override
    public SliceList<TakeCoupon> findAll(CouponQuery query) {
        var page = this.repository.findAll(query);
        return PageList.of(page.getContent())
                .page(query.getPage()).limit(query.getLimit())
                .totalSize(page.getTotalElements())
                .cast();
    }

    @Override
    public long count(CouponQuery query) {
        return 0;
    }
}
