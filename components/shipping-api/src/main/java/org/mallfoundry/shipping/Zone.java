package org.mallfoundry.shipping;

import java.math.BigDecimal;
import java.util.List;

public interface Zone {

    String getId();

    String getName();

    void setName(String name);

    List<String> getLocations();

    void setLocations(List<String> locations);

    BigDecimal getFirstCost();

    void setFirstCost(BigDecimal cost);

    BigDecimal getFirst();

    void setFirst(BigDecimal amount);

    BigDecimal getAdditionalCost();

    void setAdditionalCost(BigDecimal cost);

    BigDecimal getAdditional();

    void setAdditional(BigDecimal amount);
}
