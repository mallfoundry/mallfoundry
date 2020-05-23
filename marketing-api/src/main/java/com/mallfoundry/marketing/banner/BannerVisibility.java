package com.mallfoundry.marketing.banner;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BannerVisibility {
    VISIBLE, HIDDEN;

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
