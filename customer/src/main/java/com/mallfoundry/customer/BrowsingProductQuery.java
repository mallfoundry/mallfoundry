/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mallfoundry.customer;

import com.mallfoundry.data.OffsetLimit;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BrowsingProductQuery extends OffsetLimit {

    private String customerId;

    private Date browsingTime;

    public static BrowsingProductQueryBuilder builder() {
        return new BrowsingProductQueryBuilder();
    }

    public static class BrowsingProductQueryBuilder {
        private final com.mallfoundry.browsing.BrowsingProductQuery query;

        public BrowsingProductQueryBuilder() {
            this(new com.mallfoundry.browsing.BrowsingProductQuery());
        }

        public BrowsingProductQueryBuilder(com.mallfoundry.browsing.BrowsingProductQuery query) {
            this.query = query;
        }

        public BrowsingProductQueryBuilder customerId(String customerId) {
            this.query.setCustomerId(customerId);
            return this;
        }

        public BrowsingProductQueryBuilder browsingTime(Date browsingTime) {
            this.query.setBrowsingTime(browsingTime);
            return this;
        }

        public BrowsingProductQueryBuilder offset(int offset) {
            this.query.setOffset(offset);
            return this;
        }

        public BrowsingProductQueryBuilder limit(int limit) {
            this.query.setLimit(limit);
            return this;
        }

        public com.mallfoundry.browsing.BrowsingProductQuery build() {
            return this.query;
        }

    }

}
