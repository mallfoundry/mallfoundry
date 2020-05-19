package com.mallfoundry.browsing;

import com.mallfoundry.data.Pageable;
import com.mallfoundry.data.PageableBuilder;

import java.util.Date;

public interface BrowsingProductQuery extends Pageable {

    String getBrowserId();

    void setBrowserId(String browserId);

    Date getBrowsingTime();

    void setBrowsingTime(Date browsingTime);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends PageableBuilder<BrowsingProductQuery, Builder> {

        Builder browserId(String browserId);

        Builder browsingTime(Date browsingTime);
    }

    abstract class BuilderSupport implements Builder {

        protected final BrowsingProductQuery query;

        public BuilderSupport(BrowsingProductQuery query) {
            this.query = query;
        }

        @Override
        public Builder browserId(String browserId) {
            this.query.setBrowserId(browserId);
            return this;
        }

        @Override
        public Builder browsingTime(Date browsingTime) {
            this.query.setBrowsingTime(browsingTime);
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
        public BrowsingProductQuery build() {
            return this.query;
        }
    }
}
