/*
 * Copyright 2020 the original author or authors.
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

package com.mallfoundry.store;

import com.mallfoundry.data.PageableSupport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreQuery extends PageableSupport {

    private String ownerId;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private StoreQuery query;

        public Builder() {
            this.query = new StoreQuery();
        }

        public Builder page(int page) {
            this.query.setPage(page);
            return this;
        }

        public Builder limit(int limit) {
            this.query.setLimit(limit);
            return this;
        }

        public Builder ownerId(String ownerId) {
            this.query.setOwnerId(ownerId);
            return this;
        }

        public StoreQuery build() {
            return this.query;
        }
    }
}
