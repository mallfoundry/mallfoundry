package org.mallfoundry.tracking.provider;

import org.junit.jupiter.api.Test;
import org.mallfoundry.shipping.CarrierCode;
import org.mallfoundry.tracking.TrackingProvider;

import java.io.UnsupportedEncodingException;

public class TrackingProviderTests {

    @Test
    public void testGetTrack() throws UnsupportedEncodingException {
        String url = "http://api.kdniao.com";
        String apiKey = "";
        String eBusinessId = "";
        TrackingProvider provider = new KdniaoTrackingProvider(url, apiKey, eBusinessId);
//        provider.getTracking("SF", "1234561");
        provider.getTracker(CarrierCode.YTO, "118650888018");
    }
}
