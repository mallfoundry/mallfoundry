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

package org.mallfoundry.storage.acl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class InternalOwner implements Owner {

    @Column(name = "owner_type_")
    private String type;

    @Column(name = "owner_name_")
    private String name;

    public InternalOwner(String type, String name) {
        this.setType(type);
        this.setName(name);
    }

    public static InternalOwner of(Owner owner) {
        return new InternalOwner(owner.getType(), owner.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InternalOwner)) {
            return false;
        }
        InternalOwner that = (InternalOwner) o;

        return Objects.equals(type, that.type)
                && Objects.equals(name, that.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }
}
