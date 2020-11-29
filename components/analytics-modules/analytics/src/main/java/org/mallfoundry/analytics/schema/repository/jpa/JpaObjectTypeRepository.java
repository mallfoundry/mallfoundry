/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.analytics.schema.repository.jpa;

import org.mallfoundry.analytics.schema.ObjectType;
import org.mallfoundry.analytics.schema.ObjectTypeRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaObjectTypeRepository implements ObjectTypeRepository {

    private final JpaObjectTypeRepositoryDelegate repository;

    public JpaObjectTypeRepository(JpaObjectTypeRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public ObjectType create(String id) {
        return new JpaObjectType(id);
    }

    @Override
    public ObjectType save(ObjectType objectType) {
        return this.repository.save(JpaObjectType.of(objectType));
    }

    @Override
    public Optional<ObjectType> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public void delete(ObjectType objectType) {
        this.repository.delete(JpaObjectType.of(objectType));
    }
}
