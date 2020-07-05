package org.mallfoundry;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class AssertTests {

    @Test
    public void testTrue() {
        Assert.isTrue(true, "test true");
    }
}
