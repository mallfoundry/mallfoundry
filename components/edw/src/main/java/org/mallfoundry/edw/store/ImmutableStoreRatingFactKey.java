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

package org.mallfoundry.edw.store;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ImmutableStoreRatingFactKey implements StoreRatingFactKey {

    private String tenantKey;

    private String storeKey;

    private int dateKey;

    private int timeKey;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ImmutableStoreRatingFactKey)) {
            return false;
        }
        ImmutableStoreRatingFactKey that = (ImmutableStoreRatingFactKey) object;
        return dateKey == that.dateKey
                && timeKey == that.timeKey
                && Objects.equals(tenantKey, that.tenantKey)
                && Objects.equals(storeKey, that.storeKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantKey, storeKey, dateKey, timeKey);
    }
}
