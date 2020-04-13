package com.mallfoundry.carrier;

import java.util.List;

public interface CarrierService {

    Carrier createCarrier(String code, String name);

    List<Carrier> getCarriers();

    void addCarrier(Carrier carrier);

    Carrier getCarrier(String code);

}
