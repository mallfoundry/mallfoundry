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

package org.mallfoundry.tracking;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.shipping.CarrierCode;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class InternalTrackerId implements Serializable {

    private CarrierCode carrierCode;

    private String trackingNumber;

    public InternalTrackerId(CarrierCode carrierCode, String trackingNumber) {
        this.carrierCode = carrierCode;
        this.trackingNumber = trackingNumber;
    }

    public static InternalTrackerId of(CarrierCode carrierCode, String trackingNumber) {
        return new InternalTrackerId(carrierCode, trackingNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternalTrackerId that = (InternalTrackerId) o;
        return Objects.equals(carrierCode, that.carrierCode)
                && Objects.equals(trackingNumber, that.trackingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carrierCode, trackingNumber);
    }
}
