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

package org.mallfoundry.discuss;

import org.mallfoundry.util.ObjectBuilder;

public interface Author extends ObjectBuilder.ToBuilder<Author.Builder> {

    String getId();

    void setId(String id);

    String getNickname();

    void setNickname(String nickname);

    String getAvatar();

    void setAvatar(String avatar);

    AuthorType getType();

    void setType(AuthorType type);

    boolean isAnonymous();

    void anonymous();

    interface Builder extends ObjectBuilder<Author> {

        Builder nickname(String nickname);

        Builder avatar(String avatar);

        Builder type(AuthorType type);

        Builder anonymous(boolean anonymous);

        Builder anonymous();
    }
}
