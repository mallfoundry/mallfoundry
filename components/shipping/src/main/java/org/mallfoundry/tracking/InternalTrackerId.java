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
