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

import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public interface BrowsingProduct extends Serializable, ObjectBuilder.ToBuilder<BrowsingProduct.Builder> {

    String getId();

    String getBrowserId();

    void setBrowserId(String browserId);

    String getName();

    void setName(String name);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    int getHits();

    Date getBrowsingTime();

    void hit();

    interface Builder extends ObjectBuilder<BrowsingProduct> {

        Builder browserId(String browserId);

        Builder name(String name);

        Builder imageUrl(String imageUrl);

        Builder price(BigDecimal price);

        Builder hit();
    }
}
