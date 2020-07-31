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

import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.List;

/**
 * 商品订单评价对象，当客户完成订单以后可以对订单进行评价操作。
 *
 * @author Zhi Tang
 */
public interface OrderReview extends ObjectBuilder.ToBuilder<OrderReview.Builder> {

    String getId();

    void setId(String id);

    String getOrderId();

    String getItemId();

    void setItemId(String itemId);

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    int getRating();

    void setRating(int rating);

    OrderReviewStatus getStatus();

    boolean isAnonymous();

    /**
     * 匿名评价。
     */
    void anonymous();

    String getReviewer();

    void setReviewer(String reviewer);

    String getReviewerId();

    void setReviewerId(String reviewerId);

    List<String> getTags();

    void setTags(List<String> tags);

    /**
     * 获得商品订单评价内容。
     */
    String getBody();

    void setBody(String body);

    String getRawBody();

    void setRawBody(String rawBody);

    BodyType getBodyType();

    void setBodyType(BodyType bodyType);

/*    List<String> getVideoUrls();

    List<String> getImageUrls();*/

    Date getReviewedTime();

    Date getCreatedTime();

    void create() throws OrderReviewException;

    void approve() throws OrderReviewException;

    void disapprove() throws OrderReviewException;

    /**
     * 订单评价内容类型，用于在渲染内容的时候使用。
     */
    enum BodyType {
        HTML, JSON, TEXT
    }


    interface Builder extends ObjectBuilder<OrderReview> {

        Builder id(String id);

        Builder itemId(String itemId);

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder rating(int rating);

        Builder anonymous();

        Builder anonymous(boolean anonymous);

        Builder reviewer(String reviewer);

        Builder reviewerId(String reviewerId);

        Builder tags(List<String> tags);

        Builder body(String body);

        Builder rawBody(String rawBody);

        Builder bodyType(BodyType bodyType);

        Builder htmlBody();

        Builder textBody();

        Builder jsonBody();
    }

}
