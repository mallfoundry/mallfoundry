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

package com.mallfoundry.store.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
public class CustomCollection {

    public static final String ROOT_ID = "1";

    private String id;

    @JsonProperty("parent_id")
    private String parentId;

    @JsonProperty("store_id")
    private String storeId;

    private String name;

    private boolean enabled;

    private short position;

    public void setRootIdToParentId() {
        this.setParentId(ROOT_ID);
    }

    public void validStoreId() {
        Assert.isNull(this.getStoreId(), "store id is required.");
    }

    public void validParentId() {
        Assert.isNull(this.getParentId(), "parent id is required.");
    }
}
