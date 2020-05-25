package org.mallfoundry.follow;

import org.mallfoundry.data.Pageable;
import org.mallfoundry.data.PageableBuilder;

public interface FollowProductQuery extends Pageable {

    String getFollowerId();

    void setFollowerId(String followerId);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends PageableBuilder<FollowProductQuery, Builder> {
        Builder followerId(String followerId);
    }

    class BuilderSupport implements Builder {

        private final FollowProductQuery query;

        public BuilderSupport(FollowProductQuery query) {
            this.query = query;
        }

        @Override
        public Builder followerId(String followerId) {
            this.query.setFollowerId(followerId);
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
        public FollowProductQuery build() {
            return this.query;
        }
    }
}
