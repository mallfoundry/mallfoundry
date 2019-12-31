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

package com.mallfoundry.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class MenuIcon {

    @JsonProperty("icon_type")
    @Column(name = "icon_type_")
    private Type type;

    @JsonProperty("icon")
    @Column(name = "icon_")
    private String value;

    public MenuIcon(String icon) {
        this.setType(Type.IMAGE_URL);
        this.setValue(icon);
    }

    public MenuIcon(Type type, String icon) {
        this.setType(type);
        this.setValue(icon);
    }

    public enum Type {

        IMAGE,
        IMAGE_URL;

        @JsonValue
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
