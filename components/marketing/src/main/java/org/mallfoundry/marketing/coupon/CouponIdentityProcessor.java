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

import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class CouponIdentityProcessor implements CouponProcessor {

    private static final String MARKETING_COUPON_ID_VALUE_NAME = "marketing.coupon.id";

    private static final String MARKETING_RECEIVE_COUPON_ID_VALUE_NAME = "marketing.receive.coupon.id";

    @Override
    public Coupon preProcessBeforeAddCoupon(Coupon coupon) {
        coupon.setId(PrimaryKeyHolder.next(MARKETING_COUPON_ID_VALUE_NAME));
        return coupon;
    }

    @Override
    public TakeCoupon preProcessBeforeTakeCoupon(Coupon coupon, TakeCoupon takeCoupon) {
        takeCoupon.setId(PrimaryKeyHolder.next(MARKETING_RECEIVE_COUPON_ID_VALUE_NAME));
        return takeCoupon;
    }
}
