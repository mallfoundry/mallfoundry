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

package org.mallfoundry.browsing;

import java.math.BigDecimal;
import java.util.Date;

public abstract class BrowsingProductSupport implements MutableBrowsingProduct {

    @Override
    public void hit() {
        this.setBrowsingTime(new Date());
        this.setHits(this.getHits() + 1);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        protected final BrowsingProduct browsingProduct;

        public BuilderSupport(BrowsingProduct browsingProduct) {
            this.browsingProduct = browsingProduct;
        }

        @Override
        public Builder browserId(String browserId) {
            this.browsingProduct.setBrowserId(browserId);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.browsingProduct.setName(name);
            return this;
        }

        @Override
        public Builder imageUrl(String imageUrl) {
            this.browsingProduct.setImageUrl(imageUrl);
            return this;
        }

        @Override
        public Builder price(BigDecimal price) {
            this.browsingProduct.setPrice(price);
            return this;
        }

        @Override
        public Builder hit() {
            this.browsingProduct.hit();
            return this;
        }

        @Override
        public BrowsingProduct build() {
            return this.browsingProduct;
        }
    }

}
