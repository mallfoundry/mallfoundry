package org.mallfoundry.analytics.stream;

import org.junit.jupiter.api.Test;
import org.mallfoundry.TestSpringBootApplication;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderPlacedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = TestSpringBootApplication.class)
public class StreamTests {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Test
    public void testStream() {

        var testOrder = new TestOrder();
        testOrder.setId("1");
        testOrder.setCustomerId("1");
        testOrder.setCreatedTime(new Date());

        var item1 = new TestOrderItem();
        item1.setId("1");
        item1.setPrice(BigDecimal.valueOf(10.11));
        item1.setQuantity(11);
        item1.setShippingCost(BigDecimal.valueOf(10));
        testOrder.setItems(List.of(item1));
        this.eventPublisher.publishEvent(new OrderPlacedEvent() {
            @Override
            public Order getOrder() {
                return testOrder;
            }

            @Override
            public long getTimestamp() {
                return System.currentTimeMillis();
            }
        });
    }
}
