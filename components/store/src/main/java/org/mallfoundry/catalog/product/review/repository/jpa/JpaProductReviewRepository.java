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

package org.mallfoundry.catalog.product.review.repository.jpa;

import org.mallfoundry.catalog.product.review.ProductReview;
import org.mallfoundry.catalog.product.review.ProductReviewQuery;
import org.mallfoundry.catalog.product.review.ProductReviewRepository;
import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.springframework.data.util.CastUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JpaProductReviewRepository implements ProductReviewRepository {

    private final JpaProductReviewRepositoryDelegate repository;

    public JpaProductReviewRepository(JpaProductReviewRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public ProductReview create(String reviewId) {
        return new JpaProductReview(reviewId);
    }

    @Override
    public ProductReview save(ProductReview review) {
        return Function.<ProductReview>identity()
                .<JpaProductReview>compose(this.repository::save)
                .compose(JpaProductReview::of)
                .apply(review);
    }

    @Override
    public List<ProductReview> saveAll(List<ProductReview> reviews) {
        var jpaReviews = reviews.stream().map(JpaProductReview::of).collect(Collectors.toList());
        return CastUtils.cast(this.repository.saveAll(jpaReviews));
    }

    @Override
    public Optional<ProductReview> findById(String reviewId) {
        return CastUtils.cast(this.repository.findById(reviewId));
    }

    @Override
    public SliceList<ProductReview> findAll(ProductReviewQuery query) {
        var page = this.repository.findAll(query);
        return CastUtils.cast(PageList.of(page.getContent()).page(query.getPage()).limit(query.getLimit()).totalSize(page.getTotalElements()));
    }
}
