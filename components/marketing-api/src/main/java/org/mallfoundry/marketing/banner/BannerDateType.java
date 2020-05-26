package org.mallfoundry.marketing.banner;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BannerDateType {
    ALWAYS, CUSTOM;

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
