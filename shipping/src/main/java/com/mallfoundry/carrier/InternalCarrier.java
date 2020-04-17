package com.mallfoundry.carrier;

import com.mallfoundry.shipping.Carrier;
import com.mallfoundry.shipping.CarrierCode;
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
