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

package org.mallfoundry.catalog.product.repository.jpa;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductQuery;
import org.mallfoundry.catalog.product.repository.JdbcProductRepository;
import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.data.repository.JpaRepository;
import org.springframework.data.util.CastUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DelegatingJpaProductRepository implements JdbcProductRepository, JpaRepository {

    private final JpaProductRepository repository;

    public DelegatingJpaProductRepository(JpaProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product create(String id) {
        return new JpaProduct(id);
    }

    @Override
    public Product save(Product product) {
        return this.repository.save(JpaProduct.of(product));
    }

    @Override
    public List<Product> saveAll(Collection<Product> products) {
        return CastUtils.cast(this.repository.saveAll(
                CollectionUtils.emptyIfNull(products).stream().map(JpaProduct::of).collect(Collectors.toList())));
    }

    @Override
    public Optional<Product> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public List<Product> findAllById(Collection<String> ids) {
        return CastUtils.cast(this.repository.findAllById(ids));
    }

    @Override
    public SliceList<Product> findAll(ProductQuery query) {
        var page = this.repository.findAll(query);
        return PageList.of(page.getContent())
                .page(page.getNumber()).limit(query.getLimit())
                .totalSize(page.getTotalElements())
                .cast();
    }

    @Override
    public void delete(Product product) {
        this.repository.delete(JpaProduct.of(product));
    }
}
