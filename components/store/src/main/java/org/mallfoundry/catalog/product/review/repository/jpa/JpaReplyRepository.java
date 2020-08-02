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

import org.mallfoundry.catalog.product.review.Reply;
import org.mallfoundry.catalog.product.review.ReplyRepository;

public class JpaReplyRepository implements ReplyRepository {

    private final JpaReplyRepositoryDelegate repository;

    public JpaReplyRepository(JpaReplyRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public Reply save(Reply comment) {
        return this.repository.save(JpaReply.of(comment));
    }
}
