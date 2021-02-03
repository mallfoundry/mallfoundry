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

package org.mallfoundry.order.review.repository.jpa;

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.order.OrderReview;
import org.mallfoundry.order.review.OrderReviewQuery;
import org.mallfoundry.order.review.OrderReviewRepository;

public class DelegatingJpaOrderReviewRepository implements OrderReviewRepository {

    private final JpaOrderReviewRepository repository;

    public DelegatingJpaOrderReviewRepository(JpaOrderReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public SliceList<OrderReview> findAll(OrderReviewQuery query) {
        return PageList.of(this.repository.findAll(query));
    }
}
