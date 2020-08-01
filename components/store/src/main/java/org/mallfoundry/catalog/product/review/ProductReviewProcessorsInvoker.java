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

package org.mallfoundry.catalog.product.review;

import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.processor.ProcessorsInvoker;

import java.util.List;

public class ProductReviewProcessorsInvoker {

    private final List<ProductReviewProcessor> processors;

    public ProductReviewProcessorsInvoker(List<ProductReviewProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    public List<ProductReview> invokePreProcessAddProductReviews(List<ProductReview> reviews) {
        return ProcessorsInvoker.invokeBiFunctionProcessors(this.processors, reviews, ProductReviewProcessor::preProcessAddProductReviews);
    }

    public ProductReview invokePreProcessApproveProductReview(ProductReview review) {
        return review;
    }

    public ProductReview invokePreProcessDisapproveProductReview(ProductReview review) {
        return review;
    }
}
