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

import org.mallfoundry.catalog.product.review.Review;
import org.mallfoundry.catalog.product.review.ReviewQuery;
import org.mallfoundry.catalog.product.review.ReviewRepository;
import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.springframework.data.util.CastUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DelegatingJpaReviewRepository implements ReviewRepository {

    private final JpaReviewRepository repository;

    public DelegatingJpaReviewRepository(JpaReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Review create(String reviewId) {
        return new JpaReview(reviewId);
    }

    @Override
    public Review save(Review review) {
        return Function.<Review>identity()
                .<JpaReview>compose(this.repository::save)
                .compose(JpaReview::of)
                .apply(review);
    }

    @Override
    public List<Review> saveAll(List<Review> reviews) {
        var jpaReviews = reviews.stream().map(JpaReview::of).collect(Collectors.toList());
        return CastUtils.cast(this.repository.saveAll(jpaReviews));
    }

    @Override
    public Optional<Review> findById(String reviewId) {
        return CastUtils.cast(this.repository.findById(reviewId));
    }

    @Override
    public SliceList<Review> findAll(ReviewQuery query) {
        var page = this.repository.findAll(query);
        return CastUtils.cast(PageList.of(page.getContent()).page(query.getPage()).limit(query.getLimit()).totalSize(page.getTotalElements()));
    }

    @Override
    public long count(ReviewQuery query) {
        return this.repository.count(query);
    }
}
