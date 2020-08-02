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

import org.apache.commons.lang3.StringUtils;

public abstract class AuthorSupport implements MutableAuthor {

    @Override
    public void anonymous() {
        this.setAnonymous(true);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        protected final AuthorSupport author;

        public BuilderSupport(AuthorSupport author) {
            this.author = author;
        }

        @Override
        public Builder nickname(String nickname) {
            this.author.setNickname(nickname);
            return this;
        }

        @Override
        public Builder avatar(String avatar) {
            if (StringUtils.isNotBlank(avatar)) {
                this.author.setAvatar(avatar);
            }
            return this;
        }

        @Override
        public Builder type(AuthorType type) {
            this.author.setType(type);
            return this;
        }

        @Override
        public Builder anonymous(boolean anonymous) {

            return anonymous ? this.anonymous() : this;
        }

        @Override
        public Builder anonymous() {
            this.author.anonymous();
            return this;
        }

        @Override
        public Author build() {
            return this.author;
        }
    }
}
