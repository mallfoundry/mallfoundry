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

package org.mallfoundry.catalog.collection.repository.jpa;

import org.mallfoundry.catalog.collection.Collection;
import org.mallfoundry.catalog.collection.CollectionQuery;
import org.mallfoundry.catalog.collection.CollectionRepository;
import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.springframework.data.util.CastUtils;

import java.util.List;
import java.util.Optional;

public class DelegatingJpaCollectionRepository implements CollectionRepository {

    private final JpaCollectionRepository repository;

    public DelegatingJpaCollectionRepository(JpaCollectionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection create(String id) {
        return new JpaCollection(id);
    }

    @Override
    public Collection save(Collection collection) {
        return this.repository.save(JpaCollection.of(collection));
    }

    @Override
    public List<Collection> saveAll(List<Collection> collections) {
        return CastUtils.cast(this.repository.saveAll(CastUtils.cast(collections)));
    }

    @Override
    public void delete(Collection collection) {
        this.repository.delete(JpaCollection.of(collection));
    }

    @Override
    public Optional<Collection> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public List<Collection> findAllById(java.util.Collection<String> ids) {
        return CastUtils.cast(this.repository.findAllById(ids));
    }

    @Override
    public SliceList<Collection> findAll(CollectionQuery query) {
        var page = this.repository.findAll(query);
        return PageList.of(page.getContent())
                .page(query.getPage()).limit(query.getLimit())
                .totalSize(page.getTotalElements()).cast();
    }
}
