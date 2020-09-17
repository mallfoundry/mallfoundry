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

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.util.Copies;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultCouponService implements CouponService, CouponProcessorInvoker {

    private List<CouponProcessor> processors;

    private final CouponRepository couponRepository;

    private final TakeCouponRepository takeCouponRepository;

    public DefaultCouponService(CouponRepository couponRepository,
                                TakeCouponRepository takeCouponRepository) {
        this.couponRepository = couponRepository;
        this.takeCouponRepository = takeCouponRepository;
    }

    public void setProcessors(List<CouponProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public CouponQuery createCouponQuery() {
        return new DefaultCouponQuery();
    }

    @Override
    public Coupon createCoupon(String couponId) {
        return this.couponRepository.create(couponId);
    }

    @Transactional
    @Override
    public Coupon addCoupon(Coupon coupon) {
        coupon = this.invokePreProcessBeforeAddCoupon(coupon);
        coupon.create();
        coupon = this.invokePreProcessAfterAddCoupon(coupon);
        return this.couponRepository.save(coupon);
    }

    @Override
    public Coupon getCoupon(String couponId) {
        return this.requiredCoupon(couponId);
    }

    @Override
    public SliceList<Coupon> getCoupons(CouponQuery query) {
        return this.couponRepository.findAll(query);
    }

    private Coupon requiredCoupon(String couponId) {
        return this.couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponException(CouponMessages.notFound()));
    }

    @Transactional
    @Override
    public Coupon updateCoupon(Coupon source) {
        var coupon = this.requiredCoupon(source.getId());
        Copies.notBlank(source::getName).trim(coupon::setName)
                .notBlank(source::getDescription).trim(coupon::setDescription)
                .notNull(source::getDiscountAmount).set(coupon::setDiscountAmount)
                .notNull(source::getDiscountPercent).set(coupon::setDiscountPercent)
                .notNull(source::getDiscountMinAmount).set(coupon::setDiscountMinAmount)
                .notNull(source::getDiscountMaxAmount).set(coupon::setDiscountMaxAmount)
                .notNull(source::getMinAmount).set(coupon::setMinAmount)
                .notNull(source::getMaxAmount).set(coupon::setMaxAmount)
                .notNull(source::getStartTime).set(coupon::setStartTime)
                .notNull(source::getEndTime).set(coupon::setEndTime);
        if (source.getIssuingCount() > 0) {
            coupon.setIssuingCount(source.getIssuingCount());
        }
        return this.couponRepository.save(coupon);
    }

    @Transactional
    @Override
    public void pauseCoupon(String couponId) {
        var coupon = this.requiredCoupon(couponId);
        coupon.pause();
        this.couponRepository.save(coupon);
    }

    @Transactional
    @Override
    public void deleteCoupon(String couponId) {
        var coupon = this.requiredCoupon(couponId);
        this.couponRepository.delete(coupon);
    }

    @Override
    public TakeCoupon createTakeCoupon(String takeId) {
        return this.takeCouponRepository.create(takeId);
    }

    private void tryTakeLimit(Coupon coupon, TakeCoupon takeCoupon) {
        // 每个顾客领取的上限。
        if (0 < coupon.getTakeLimitPerCustomer()) {
            var takeCount = this.takeCouponRepository.count(
                    this.createCouponQuery().toBuilder()
                            .customerId(takeCoupon.getCustomerId())
                            .storeId(coupon.getStoreId()).build());
            if (coupon.getTakeLimitPerCustomer() <= takeCount) {
                throw new TakeCouponException("");
            }
        }
    }

    @Transactional
    @Override
    public TakeCoupon takeCoupon(TakeCoupon takeCoupon) throws TakeCouponException {
        var coupon = this.requiredCoupon(takeCoupon.getCouponId());
        // 如果没有设置顾客标识，使用当前用户标识。
        if (StringUtils.isBlank(takeCoupon.getCustomerId())) {
            takeCoupon.setCustomerId(SubjectHolder.getSubject().getId());
        }
        takeCoupon = this.invokePreProcessBeforeTakeCoupon(coupon, takeCoupon);
        // 判断是否符合领取条件。
        this.tryTakeLimit(coupon, takeCoupon);
        takeCoupon = coupon.take(takeCoupon);
        this.couponRepository.save(coupon);
        return this.takeCouponRepository.save(takeCoupon);
    }

    @Override
    public SliceList<TakeCoupon> getTakeCoupons(CouponQuery query) {
        return this.takeCouponRepository.findAll(query);
    }

    @Override
    public long countTakeCoupons(CouponQuery query) {
        return this.takeCouponRepository.count(query);
    }

    @Override
    public Coupon invokePreProcessBeforeAddCoupon(Coupon coupon) {
        return Processors.stream(this.processors)
                .map(CouponProcessor::preProcessBeforeAddCoupon)
                .apply(coupon);
    }

    @Override
    public Coupon invokePreProcessAfterAddCoupon(Coupon coupon) {
        return Processors.stream(this.processors)
                .map(CouponProcessor::preProcessAfterAddCoupon)
                .apply(coupon);
    }

    @Override
    public TakeCoupon invokePreProcessBeforeTakeCoupon(Coupon coupon, TakeCoupon receiveCoupon) {
        return Processors.stream(this.processors)
                .<TakeCoupon>map((processor, identity) -> processor.preProcessBeforeTakeCoupon(coupon, identity))
                .apply(receiveCoupon);
    }
}
