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

import org.mallfoundry.store.StoreService;
import org.mallfoundry.validation.ValidationHolder;

public class CouponValidateProcessor implements CouponProcessor {

    private final StoreService storeService;

    public CouponValidateProcessor(StoreService storeService) {
        this.storeService = storeService;
    }

    @Override
    public Coupon preProcessBeforeAddCoupon(Coupon coupon) {
        var store = this.storeService.getStore(this.storeService.createStoreId(coupon.getStoreId()));
        coupon.setTenantId(store.getTenantId());
        return coupon;
    }

    @Override
    public Coupon preProcessAfterAddCoupon(Coupon coupon) {
        ValidationHolder.validate(coupon, "coupon");
        return coupon;
    }
}
