package org.mallfoundry.autoconfigure.shipping;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        RateAutoConfiguration.class,
        TrackAutoConfiguration.class,
})
public class ShippingAutoConfiguration {
}
