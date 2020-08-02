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
import org.mallfoundry.catalog.product.review.Reply;
import org.mallfoundry.catalog.product.review.ReplySupport;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_catalog_product_review_comment")
public class JpaReply extends ReplySupport {

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

    @ManyToOne(targetEntity = JpaReply.class)
    @JoinColumn(name = "reply_to_id_")
    private Reply replyTo;

    @Column(name = "created_time_")
    private Date createdTime;

    public JpaReply(String id) {
        this.setId(id);
    }

    public static JpaReply of(Reply comment) {
        if (comment instanceof JpaReply) {
            return (JpaReply) comment;
        }
        var target = new JpaReply();
        BeanUtils.copyProperties(comment, target);
        return target;
    }
}
