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

import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.discuss.Author;
import org.mallfoundry.discuss.BodyType;
import org.mallfoundry.discuss.Topic;
import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.List;

public interface Review extends Topic, ObjectBuilder.ToBuilder<Review.Builder> {

    String getOrderId();

    void setOrderId(String orderId);

    String getItemId();

    void setItemId(String itemId);

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    List<OptionSelection> getOptionSelections();

    void setOptionSelections(List<OptionSelection> optionSelections);

    List<String> getTags();

    void setTags(List<String> tags);

    List<String> getVideoUrls();

    void setVideoUrls(List<String> videoUrls);

    List<String> getImageUrls();

    void setImageUrls(List<String> imageUrls);

    int getRating();

    void rating(int rating);

    ReviewStatus getStatus();

    Date getReviewedTime();

    void review();

    void approve();

    void disapprove();

    ReviewReply createReply(String replyId);

    void reply(ReviewReply reply);

    /*ReviewStoreReply createStoreReply(String replyId);*/

    interface Builder extends ObjectBuilder<Review> {

        Builder id(String id);

        Builder orderId(String orderId);

        Builder itemId(String itemId);

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder optionSelections(List<OptionSelection> optionSelections);

        Builder rating(int rating);

        Builder author(Author author);

        Builder tags(List<String> tags);

        Builder body(String body);

        Builder rawBody(String rawBody);

        Builder bodyType(BodyType bodyType);

        Builder videoUrls(List<String> videoUrls);

        Builder imageUrls(List<String> imageUrls);
    }
}
