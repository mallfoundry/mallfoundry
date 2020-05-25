package org.mallfoundry.shipping;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RateTests {

    @Autowired
    private RateService rateService;

    @Test
    @Rollback(false)
    @Transactional
    public void testCreateRate() {
        var zone = rateService.createZone();
        zone.setLocations(List.of("370832", "370831", "370833"));
        zone.setName("test");
        zone.setFirst(BigDecimal.valueOf(1));
        zone.setFirstCost(BigDecimal.valueOf(10));
        zone.setAdditional(BigDecimal.valueOf(1));
        zone.setAdditionalCost(BigDecimal.valueOf(5));
        var rate = rateService.createRate("mi");
        rate.addZone(zone);
        rate.setMode(RateMode.PACKAGE);
        rateService.saveRate(rate);
    }

    @Test
    @Rollback(false)
    @Transactional
    public void testGetRate() {
        var rate = rateService.getRate("3").orElseThrow();
//        rate.
        System.out.println(rate);
    }
}
