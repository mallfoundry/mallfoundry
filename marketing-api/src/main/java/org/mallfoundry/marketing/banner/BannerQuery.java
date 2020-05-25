package org.mallfoundry.marketing.banner;

import org.mallfoundry.data.Pageable;
import org.mallfoundry.data.PageableBuilder;

import java.util.Date;
import java.util.Set;
import java.util.function.Supplier;

public interface BannerQuery extends Pageable {

    Set<BannerPage> getPages();

    void setPages(Set<BannerPage> pages);

    Set<BannerDateType> getDateTypes();

    void setDateTypes(Set<BannerDateType> dateTypes);

    Date getDateFrom();

    void setDateFrom(Date dateFrom);

    Date getDateTo();

    void setDateTo(Date dateTo);

    Set<BannerLocation> getLocations();

    void setLocations(Set<BannerLocation> locations);

    Set<BannerVisibility> getVisibilities();

    void setVisibilities(Set<BannerVisibility> visibilities);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends PageableBuilder<BannerQuery, Builder> {

        default Builder pages(Supplier<Set<BannerPage>> supplier) {
            return this.pages(supplier.get());
        }

        Builder pages(Set<BannerPage> pages);

        default Builder dateTypes(Supplier<Set<BannerDateType>> supplier) {
            return this.dateTypes(supplier.get());
        }

        Builder dateTypes(Set<BannerDateType> dateTypes);

        Builder dateFrom(Date dateFrom);

        Builder dateTo(Date dateTo);

        default Builder locations(Supplier<Set<BannerLocation>> supplier) {
            return this.locations(supplier.get());
        }

        Builder locations(Set<BannerLocation> locations);

        default Builder visibilities(Supplier<Set<BannerVisibility>> supplier) {
            return this.visibilities(supplier.get());
        }

        Builder visibilities(Set<BannerVisibility> visibilities);
    }

    abstract class BuilderSupport implements Builder {

        private final BannerQuery query;

        public BuilderSupport(BannerQuery query) {
            this.query = query;
        }

        @Override
        public Builder pages(Set<BannerPage> pages) {
            this.query.setPages(pages);
            return this;
        }

        @Override
        public Builder dateTypes(Set<BannerDateType> dateTypes) {
            this.query.setDateTypes(dateTypes);
            return this;
        }

        @Override
        public Builder dateFrom(Date dateFrom) {
            this.query.setDateFrom(dateFrom);
            return this;
        }

        @Override
        public Builder dateTo(Date dateTo) {
            this.query.setDateTo(dateTo);
            return this;
        }

        @Override
        public Builder locations(Set<BannerLocation> locations) {
            this.query.setLocations(locations);
            return this;
        }

        @Override
        public Builder visibilities(Set<BannerVisibility> visibilities) {
            this.query.setVisibilities(visibilities);
            return this;
        }

        @Override
        public Builder page(Integer page) {
            this.query.setPage(page);
            return this;
        }

        @Override
        public Builder limit(Integer limit) {
            this.query.setLimit(limit);
            return this;
        }

        @Override
        public BannerQuery build() {
            return this.query;
        }
    }
}
