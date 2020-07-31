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

package org.mallfoundry.order;

import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.review.ReviewBodyType;

import java.util.Date;
import java.util.List;

public abstract class OrderReviewSupport implements MutableOrderReview {

    @Override
    public void anonymous() {
        this.setAnonymous(true);
    }

    @Override
    public void rating(int rating) {
        this.setRating(rating);
    }

    @Override
    public void review() {
        this.setReviewedTime(new Date());
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {
        private final OrderReviewSupport review;

        protected BuilderSupport(OrderReviewSupport review) {
            this.review = review;
        }

        @Override
        public Builder id(String id) {
            this.review.setId(id);
            return this;
        }

        @Override
        public Builder itemId(String itemId) {
            this.review.setItemId(itemId);
            return this;
        }

        @Override
        public Builder productId(String productId) {
            this.review.setProductId(productId);
            return this;
        }

        @Override
        public Builder variantId(String variantId) {
            this.review.setVariantId(variantId);
            return this;
        }

        @Override
        public Builder optionSelections(List<OptionSelection> optionSelections) {
            this.review.setOptionSelections(optionSelections);
            return this;
        }

        @Override
        public Builder rating(int rating) {
            this.review.rating(rating);
            return this;
        }

        @Override
        public Builder anonymous() {
            this.review.anonymous();
            return this;
        }

        @Override
        public Builder anonymous(boolean anonymous) {
            return anonymous ? this.anonymous() : this;
        }

        @Override
        public Builder reviewer(String reviewer) {
            this.review.setReviewer(reviewer);
            return this;
        }

        @Override
        public Builder reviewerId(String reviewerId) {
            this.review.setReviewerId(reviewerId);
            return this;
        }

        @Override
        public Builder tags(List<String> tags) {
            this.review.setTags(tags);
            return this;
        }

        @Override
        public Builder body(String body) {
            this.review.setBody(body);
            return this;
        }

        @Override
        public Builder rawBody(String rawBody) {
            this.review.setRawBody(rawBody);
            return this;
        }

        @Override
        public Builder bodyType(ReviewBodyType bodyType) {
            this.review.setBodyType(bodyType);
            return this;
        }

        @Override
        public Builder videoUrls(List<String> videoUrls) {
            this.review.setVideoUrls(videoUrls);
            return this;
        }

        @Override
        public Builder imageUrls(List<String> imageUrls) {
            this.review.setImageUrls(imageUrls);
            return this;
        }

        @Override
        public OrderReview build() {
            return this.review;
        }
    }
}
