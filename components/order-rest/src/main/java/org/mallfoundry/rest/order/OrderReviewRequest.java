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

package org.mallfoundry.rest.order;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.order.OrderReview;
import org.mallfoundry.review.Author;
import org.mallfoundry.review.BodyType;
import org.mallfoundry.review.DefaultAuthor;

import java.util.List;

@Getter
@Setter
public class OrderReviewRequest {
    private String itemId;
    private int rating;
    private boolean anonymous;
    private List<String> tags;
    private String body;
    private String rawBody;
    private BodyType bodyType;
    private ReviewerRequest reviewer;

    public OrderReview assignTo(OrderReview review) {
        return review.toBuilder()
                .itemId(this.itemId).rating(this.rating)
                .tags(this.tags)
                .reviewer(this.reviewer.assignTo(new DefaultAuthor()))
                .body(this.body).rawBody(this.rawBody).bodyType(this.bodyType)
                .build();
    }

    @Getter
    @Setter
    static class ReviewerRequest {
        private String avatar;
        private boolean anonymous;

        public Author assignTo(Author author) {
            return author.toBuilder().avatar(this.avatar).anonymous(this.anonymous).build();
        }
    }
}
