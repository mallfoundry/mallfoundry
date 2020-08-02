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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.catalog.product.repository.jpa.convert.OptionSelectionListConverter;
import org.mallfoundry.catalog.product.review.ReviewReply;
import org.mallfoundry.catalog.product.review.Review;
import org.mallfoundry.catalog.product.review.ReviewStatus;
import org.mallfoundry.catalog.product.review.ReviewSupport;
import org.mallfoundry.data.repository.jpa.convert.StringListConverter;
import org.mallfoundry.review.Author;
import org.mallfoundry.review.BodyType;
import org.mallfoundry.review.repository.jpa.convert.AuthorConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_catalog_product_review")
public class JpaReview extends ReviewSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "order_id_")
    private String orderId;

    @Column(name = "item_id_")
    private String itemId;

    @Column(name = "product_id_")
    private String productId;

    @Column(name = "variant_id_")
    private String variantId;

    @Column(name = "option_selections_", length = 1024)
    @Convert(converter = OptionSelectionListConverter.class)
    private List<OptionSelection> optionSelections;

    @Column(name = "rating_")
    private int rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private ReviewStatus status;

    @Column(name = "reviewer_")
    @Convert(converter = AuthorConverter.class)
    private Author reviewer;

    @Convert(converter = StringListConverter.class)
    @Column(name = "tags_")
    private List<String> tags;

    @Column(name = "body_")
    private String body;

    @Column(name = "raw_body_")
    private String rawBody;

    @Enumerated(EnumType.STRING)
    @Column(name = "body_type_")
    private BodyType bodyType;

    @Convert(converter = StringListConverter.class)
    @Column(name = "video_urls_", length = 1024)
    private List<String> videoUrls;

    @Convert(converter = StringListConverter.class)
    @Column(name = "image_urls_", length = 1024)
    private List<String> imageUrls;

    @Column(name = "reviewed_time_")
    private Date reviewedTime;

    public JpaReview(String id) {
        this.id = id;
    }

    public static JpaReview of(Review review) {
        if (review instanceof JpaReview) {
            return (JpaReview) review;
        }
        var target = new JpaReview();
        BeanUtils.copyProperties(review, target);
        return target;
    }

    @Override
    public ReviewReply createReply(String replyId) {
        return new JpaReviewReply(replyId);
    }
}
