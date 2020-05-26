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

package org.mallfoundry.util;

import java.io.Serializable;
import java.util.Objects;

public interface Position extends Comparable<Position>, Serializable {

    void setPosition(Integer position);

    Integer getPosition();

    @Override
    default int compareTo(Position o) {
        int selfPosition = Objects.isNull(this.getPosition()) ? Integer.MAX_VALUE : this.getPosition();
        int otherPosition = Objects.isNull(o.getPosition()) ? Integer.MAX_VALUE : o.getPosition();
        return Integer.compare(selfPosition, otherPosition);
    }
}