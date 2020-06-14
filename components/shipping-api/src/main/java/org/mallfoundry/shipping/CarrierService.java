package org.mallfoundry.shipping;

import java.util.List;
import java.util.Optional;

public interface CarrierService {

    Carrier createCarrier(String code, String name);

    List<Carrier> getCarriers();

    void addCarrier(Carrier carrier);

    Optional<Carrier> getCarrier(CarrierCode code);

}
