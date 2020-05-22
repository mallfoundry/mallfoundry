package com.mallfoundry.rest.shipping;

import com.mallfoundry.shipping.Carrier;
import com.mallfoundry.shipping.CarrierService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/v1")
@RestController
public class CarrierResourceV1 {

    private final CarrierService carrierService;

    public CarrierResourceV1(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    @GetMapping("/carriers")
    public List<Carrier> getCarriers() {
        return this.carrierService.getCarriers();
    }
}
