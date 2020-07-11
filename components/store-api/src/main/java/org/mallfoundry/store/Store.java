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

package org.mallfoundry.store;

import java.util.Date;

public interface Store {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    StoreStatus getStatus();

    String getDomain();

    void setDomain(String domain);

    String getLogoUrl();

    void setLogoUrl(String logoUrl);

    String getOwnerId();

    void setOwnerId(String ownerId);

    String getIndustry();

    void setIndustry(String industry);

    String getDescription();

    void setDescription(String description);

    StoreAddress getPrimaryAddress();

    void setPrimaryAddress(StoreAddress address);

    Date getCreatedTime();

    void initialize();

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {
        private final Store store;

        public Builder(Store store) {
            this.store = store;
        }

        public Builder name(String name) {
            this.store.setName(name);
            return this;
        }

        public Builder logoUrl(String logoUrl) {
            this.store.setLogoUrl(logoUrl);
            return this;
        }

        public Builder ownerId(String ownerId) {
            this.store.setOwnerId(ownerId);
            return this;
        }

        Store build() {
            return this.store;
        }
    }
}
