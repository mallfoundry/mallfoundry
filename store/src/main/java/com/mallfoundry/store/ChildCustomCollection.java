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

package com.mallfoundry.store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("child_custom_collection")
public class ChildCustomCollection extends CustomCollection {

    @JsonIgnore
    @ManyToOne
    private CustomCollection parent;

    public ChildCustomCollection(String name) {
        super(name);
    }

    public ChildCustomCollection(CustomCollection parent, String name) {
        super(parent.getStoreId(), name);
        this.setParent(parent);
    }

    @Embedded
    @Override
    public StoreId getStoreId() {
        return Objects.nonNull(this.getParent()) ? this.getParent().getStoreId() : super.getStoreId();
    }
}
