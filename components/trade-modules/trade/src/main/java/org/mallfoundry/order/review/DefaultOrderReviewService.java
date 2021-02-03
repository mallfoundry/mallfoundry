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

package org.mallfoundry.order.review;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.order.OrderReview;
import org.mallfoundry.processor.Processors;

import java.util.List;

public class DefaultOrderReviewService implements OrderReviewService, OrderReviewProcessorInvoker {

    private final OrderReviewRepository orderReviewRepository;

    private List<OrderReviewProcessor> processors;

    public DefaultOrderReviewService(OrderReviewRepository orderReviewRepository) {
        this.orderReviewRepository = orderReviewRepository;
    }

    public void setProcessors(List<OrderReviewProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public OrderReviewQuery createOrderReviewQuery() {
        return new DefaultOrderReviewQuery();
    }

    @Override
    public SliceList<OrderReview> getOrderReviews(OrderReviewQuery query) {
        return this.orderReviewRepository.findAll(
                this.invokePreProcessBeforeGetOrderReviews(query));
    }

    @Override
    public OrderReviewQuery invokePreProcessBeforeGetOrderReviews(OrderReviewQuery query) {
        return Processors.stream(this.processors)
                .map(OrderReviewProcessor::preProcessBeforeGetOrderReviews)
                .apply(query);
    }
}
