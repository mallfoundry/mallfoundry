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

package org.mallfoundry.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class InternalSearchTermId implements Serializable {

    private String customerId;

    private String term;

    public InternalSearchTermId(String customerId, String term) {
        this.customerId = customerId;
        this.term = term;
    }

    public static InternalSearchTermId of(String customerId, String term) {
        return new InternalSearchTermId(customerId, term);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InternalSearchTermId)) {
            return false;
        }
        InternalSearchTermId that = (InternalSearchTermId) o;
        return Objects.equals(customerId, that.customerId)
                && Objects.equals(term, that.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, term);
    }
}
