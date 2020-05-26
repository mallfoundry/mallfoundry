package org.mallfoundry.marketing.banner;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BannerPage {
    HOME_PAGE, CATEGORY_PAGE, BRAND_PAGE, SEARCH_PAGE;

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
