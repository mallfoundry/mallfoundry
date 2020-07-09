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

package org.mallfoundry.marketing.banner.repository.jpa;

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.marketing.banner.BannerDateType;
import org.mallfoundry.marketing.banner.BannerQuery;
import org.mallfoundry.marketing.banner.BannerRepository;
import org.mallfoundry.marketing.banner.InternalBanner;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Objects;

@Repository
public interface JpaBannerRepository
        extends BannerRepository, JpaRepository<InternalBanner, String>,
        JpaSpecificationExecutor<InternalBanner> {

    @Override
    default SliceList<InternalBanner> findAll(BannerQuery bannerQuery) {
        Page<InternalBanner> page = this.findAll((Specification<InternalBanner>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            // dateTypes in (always, custom)
            if (CollectionUtils.isNotEmpty(bannerQuery.getDateTypes())) {
                var dateTypes = new ArrayList<Predicate>();
                if (bannerQuery.getDateTypes().contains(BannerDateType.ALWAYS)) {
                    dateTypes.add(criteriaBuilder.equal(root.get("dateType"), BannerDateType.ALWAYS));
                }

                if (bannerQuery.getDateTypes().contains(BannerDateType.CUSTOM)) {
                    var customDateType = new ArrayList<Predicate>();
                    customDateType.add(criteriaBuilder.equal(root.get("dateType"), BannerDateType.CUSTOM));
                    if (Objects.nonNull(bannerQuery.getDateFrom())) {
                        customDateType.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateFrom"), bannerQuery.getDateFrom()));
                    }

                    if (Objects.nonNull(bannerQuery.getDateTo())) {
                        customDateType.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateTo"), bannerQuery.getDateTo()));
                    }
                    dateTypes.add(criteriaBuilder.and(customDateType.toArray(Predicate[]::new)));
                }
                predicate.getExpressions().add(criteriaBuilder.or(dateTypes.toArray(Predicate[]::new)));
            }

            if (CollectionUtils.isNotEmpty(bannerQuery.getPages())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("page")).value(bannerQuery.getPages()));
            }

            if (CollectionUtils.isNotEmpty(bannerQuery.getLocations())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("location")).value(bannerQuery.getLocations()));
            }

            if (CollectionUtils.isNotEmpty(bannerQuery.getVisibilities())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("visibility")).value(bannerQuery.getVisibilities()));
            }

            return predicate;
        }, PageRequest.of(bannerQuery.getPage() - 1, bannerQuery.getLimit()));

        return PageList.of(page.getContent()).page(page.getNumber()).limit(bannerQuery.getLimit()).totalSize(page.getTotalElements());
    }
}
