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

package org.mallfoundry.autoconfigure.marketing.coupon;

import org.mallfoundry.marketing.coupon.CouponAuthorizeProcessor;
import org.mallfoundry.marketing.coupon.CouponIdentityProcessor;
import org.mallfoundry.marketing.coupon.CouponProcessor;
import org.mallfoundry.marketing.coupon.CouponRepository;
import org.mallfoundry.marketing.coupon.CouponValidateProcessor;
import org.mallfoundry.marketing.coupon.DefaultCouponService;
import org.mallfoundry.marketing.coupon.TakeCouponRepository;
import org.mallfoundry.marketing.coupon.repository.jpa.DelegatingJpaCouponRepository;
import org.mallfoundry.marketing.coupon.repository.jpa.DelegatingJpaTakeCouponRepository;
import org.mallfoundry.marketing.coupon.repository.jpa.JpaCouponRepository;
import org.mallfoundry.marketing.coupon.repository.jpa.JpaTakeCouponRepository;
import org.mallfoundry.store.StoreService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
public class CouponAutoConfiguration {

    @Bean
    public DelegatingJpaCouponRepository delegatingJpaCouponRepository(JpaCouponRepository repository) {
        return new DelegatingJpaCouponRepository(repository);
    }

    @Bean
    public DelegatingJpaTakeCouponRepository delegatingJpaTakeCouponRepository(JpaTakeCouponRepository repository) {
        return new DelegatingJpaTakeCouponRepository(repository);
    }

    @Bean
    public DefaultCouponService defaultCouponService(@Lazy List<CouponProcessor> processors,
                                                     CouponRepository couponRepository,
                                                     TakeCouponRepository takeCouponRepository) {
        var service = new DefaultCouponService(couponRepository, takeCouponRepository);
        service.setProcessors(processors);
        return service;
    }

    @Bean
    public CouponIdentityProcessor couponIdentityProcessor() {
        return new CouponIdentityProcessor();
    }

    @Bean
    public CouponAuthorizeProcessor couponAuthorizeProcessor() {
        return new CouponAuthorizeProcessor();
    }

    @Bean
    public CouponValidateProcessor couponValidateProcessor(StoreService storeService) {
        return new CouponValidateProcessor(storeService);
    }
}
