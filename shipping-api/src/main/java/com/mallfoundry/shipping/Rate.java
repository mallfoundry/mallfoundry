package com.mallfoundry.shipping;

import java.math.BigDecimal;
import java.util.List;

public interface Rate {

    String getId();

    String getStoreId();

    String getName();

    void setName(String name);

    RateMode getMode();

    void setMode(RateMode mode);

    List<Zone> getZones();

    void setZones(List<Zone> zones);

    void addZone(Zone zone);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    BigDecimal getMinimumCost();

    BigDecimal getMaximumCost();
}
