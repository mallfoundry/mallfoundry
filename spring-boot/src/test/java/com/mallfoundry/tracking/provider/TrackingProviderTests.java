package com.mallfoundry.tracking.provider;

import com.mallfoundry.carrier.CarrierCode;
import com.mallfoundry.tracking.TrackProvider;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

public class TrackingProviderTests {

    @Test
    public void testGetTrack() throws UnsupportedEncodingException {
        String url = "http://api.kdniao.com";
        String apiKey = "";
        String eBusinessId = "";
        TrackProvider provider = new KdniaoTrackingProvider(url, apiKey, eBusinessId);
//        provider.getTracking("SF", "1234561");
        provider.getTrack(CarrierCode.YTO, "118650888018");
    }
}
