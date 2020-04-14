package com.mallfoundry.carrier;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternalCarrierService implements CarrierService {

    @Override
    public Carrier createCarrier(String code, String name) {
        return null;
    }

    @Override
    public List<Carrier> getCarriers() {
        return List.of(
                new InternalCarrier("申通快递", CarrierCode.STO),
                new InternalCarrier("中通快递", CarrierCode.ZTO),
                new InternalCarrier("圆通快递", CarrierCode.YTO));
    }

    @Override
    public void addCarrier(Carrier carrier) {

    }

    @Override
    public Carrier getCarrier(String code) {
        return null;
    }
}
