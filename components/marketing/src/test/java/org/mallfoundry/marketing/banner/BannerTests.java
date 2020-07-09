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

package org.mallfoundry.marketing.banner;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BannerTests {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    public void testSaveBanners() {

        this.bannerService.addBanner(this.bannerService.createBanner(null).toBuilder().name("name1").content("111").dateType(BannerDateType.ALWAYS).show().location(BannerLocation.TOP).build());
        this.bannerService.addBanner(this.bannerService.createBanner(null).toBuilder().name("name2").content("222").dateType(BannerDateType.ALWAYS).show().location(BannerLocation.TOP).build());
        this.bannerService.addBanner(this.bannerService.createBanner(null).toBuilder().name("name3").content("333").dateType(BannerDateType.ALWAYS).show().location(BannerLocation.TOP).build());

        this.bannerService.addBanner(this.bannerService.createBanner(null).toBuilder().name("name4").content("444").dateType(BannerDateType.CUSTOM).show().location(BannerLocation.TOP).build());
        this.bannerService.addBanner(this.bannerService.createBanner(null).toBuilder().name("name5").content("555").dateType(BannerDateType.CUSTOM).show().location(BannerLocation.TOP).build());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testQueryBanners() {

        var builder = this.entityManager.getCriteriaBuilder();
        var query = builder.createQuery(InternalBanner.class);


        var root = query.from(InternalBanner.class);

        var p1 = builder.equal(root.get("dateType"), BannerDateType.ALWAYS);
        var p2 = builder.and(builder.equal(root.get("dateType"), BannerDateType.CUSTOM),
                builder.greaterThanOrEqualTo(root.get("dateFrom"), new Date()),
                builder.lessThanOrEqualTo(root.get("dateTo"), new Date()));
        var or1 = builder.or(p1, builder.and(p2));
        var in1 = builder.in(root.get("page")).value(List.of(BannerPage.HOME_PAGE, BannerPage.CATEGORY_PAGE));
//        var dis1 = builder.disjunction();
//        dis1.getExpressions().add(or1);
//        dis1.getExpressions().add(in1);
//        var list = this.entityManager.createQuery(query.where(dis1)).getResultList();
        var conj1 = builder.conjunction();
        conj1.getExpressions().add(or1);
        conj1.getExpressions().add(in1);
        var list = this.entityManager.createQuery(query.where(builder.and())).getResultList();
        System.out.println(list);

//        var list2 = this.entityManager.createQuery("from InternalBanner where (dateType = 'ALWAYS'  or (dateType = 'CUSTOM' and '2012 09 01' < dateFrom and dateTo < '2020 05 01'))\n" +
//                "  and location = 0").getResultList();
//        System.out.println(list2);
    }
}
