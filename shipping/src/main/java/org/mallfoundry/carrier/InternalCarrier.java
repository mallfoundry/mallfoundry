package org.mallfoundry.carrier;

import org.mallfoundry.shipping.Carrier;
import org.mallfoundry.shipping.CarrierCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InternalCarrier implements Carrier {

    private String name;

    private CarrierCode code;

    public InternalCarrier(String name, CarrierCode code) {
        this.name = name;
        this.code = code;
    }
}
