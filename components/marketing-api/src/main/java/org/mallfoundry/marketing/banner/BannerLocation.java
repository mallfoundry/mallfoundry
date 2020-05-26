package org.mallfoundry.marketing.banner;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BannerLocation {
    TOP, BOTTOM;

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
