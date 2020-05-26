package org.mallfoundry.marketing.banner;

import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;

public interface Banner {

    String getName();

    void setName(String name);

    String getContent();

    void setContent(String content);

    BannerPage getPage();

    void setPage(BannerPage page);

    BannerDateType getDateType();

    void setDateType(BannerDateType dateType);

    Date getDateFrom();

    void setDateFrom(Date dateFrom);

    Date getDateTo();

    void setDateTo(Date dateTo);

    BannerLocation getLocation();

    void setLocation(BannerLocation location);

    BannerVisibility getVisibility();

    void show();

    void hide();

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<Banner> {

        Builder name(String name);

        Builder content(String content);

        Builder page(BannerPage page);

        Builder dateType(BannerDateType dateType);

        Builder dateFrom(Date dateFrom);

        Builder dateTo(Date dateTo);

        Builder location(BannerLocation location);

        Builder show();

        Builder hide();
    }

    abstract class BuilderSupport implements Builder {

        protected final Banner banner;

        protected BuilderSupport(Banner banner) {
            this.banner = banner;
        }

        @Override
        public Builder name(String name) {
            this.banner.setName(name);
            return this;
        }

        @Override
        public Builder content(String content) {
            this.banner.setContent(content);
            return this;
        }

        @Override
        public Builder page(BannerPage page) {
            this.banner.setPage(page);
            return this;
        }

        @Override
        public Builder dateType(BannerDateType dateType) {
            this.banner.setDateType(dateType);
            return this;
        }

        @Override
        public Builder dateFrom(Date dateFrom) {
            this.banner.setDateFrom(dateFrom);
            return this;
        }

        @Override
        public Builder dateTo(Date dateTo) {
            this.banner.setDateTo(dateTo);
            return this;
        }

        @Override
        public Builder location(BannerLocation location) {
            this.banner.setLocation(location);
            return this;
        }

        @Override
        public Builder show() {
            this.banner.show();
            return this;
        }

        @Override
        public Builder hide() {
            this.banner.hide();
            return this;
        }

        @Override
        public Banner build() {
            return this.banner;
        }
    }
}