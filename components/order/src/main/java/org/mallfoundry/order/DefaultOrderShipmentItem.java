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

package org.mallfoundry.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class DefaultOrderShipmentItem extends OrderShipmentItemSupport {

    @NotBlank
    private String id;

    @NotBlank
    private String itemId;

    @NotBlank
    private String productId;

    @NotBlank
    private String variantId;

    @Min(1)
    private int quantity;

    @NotBlank
    private String name;

    @NotBlank
    private String imageUrl;

    public DefaultOrderShipmentItem(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultOrderShipmentItem)) {
            return false;
        }
        DefaultOrderShipmentItem that = (DefaultOrderShipmentItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
