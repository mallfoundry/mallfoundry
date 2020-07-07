package org.mallfoundry.analytics.stream;

import org.junit.jupiter.api.Test;
import org.mallfoundry.analytics.stream.order.OrderItemFactRepository;
import org.mallfoundry.analytics.stream.order.OrderQuantityFactRepository;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@StandaloneTest
public class OrderFactTests {

    @Autowired
    private OrderItemFactRepository repository;

    @Autowired
    private OrderQuantityFactRepository quantityRepository;

    @Transactional
    @Rollback(false)
    @Test
    public void testCountAll() {
        var ids = IntStream.rangeClosed(405, 408)
                .mapToObj(Integer::toString)
                .collect(Collectors.toUnmodifiableSet());
        var items = this.repository.findAllById(ids);
        var quantities = this.repository.countAll(items);
        this.quantityRepository.saveAll(quantities);
    }
}
