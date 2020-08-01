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
import lombok.Setter;
import org.mallfoundry.review.Author;
import org.mallfoundry.review.BodyType;
import org.mallfoundry.review.repository.jpa.convert.AuthorConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class JpaProductReviewCommentBase {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "review_id_")
    private String reviewId;

    @Column(name = "author_")
    @Convert(converter = AuthorConverter.class)
    private Author author;

    @Column(name = "body_")
    private String body;

    @Column(name = "raw_body_")
    private String rawBody;

    @Enumerated(EnumType.STRING)
    @Column(name = "body_type")
    private BodyType bodyType;

    @Column(name = "created_time_")
    private Date createdTime;
}
