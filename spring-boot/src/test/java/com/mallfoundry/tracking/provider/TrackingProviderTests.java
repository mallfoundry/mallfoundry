package com.mallfoundry.tracking.provider;

import com.mallfoundry.tracking.TrackingProvider;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

public class TrackingProviderTests {

    @Test
    public void testGetTracking() throws UnsupportedEncodingException {
        TrackingProvider provider = new KdniaoTrackingProvider();
//        provider.getTracking("SF", "1234561");
        provider.getTracking("SF", "118650888018");
    }
}
