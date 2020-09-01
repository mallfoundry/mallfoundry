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

package org.mallfoundry.catalog.product.repository;

import lombok.Getter;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductQuery;
import org.mallfoundry.catalog.product.ProductRepository;
import org.mallfoundry.data.SliceList;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DelegatingProductRepository implements ProductRepository {
    @Getter
    private final ProductRepository primaryRepository;
    @Getter
    private final ProductRepository searchRepository;

    public DelegatingProductRepository(ProductRepository primaryRepository, ProductRepository searchRepository) {
        Assert.notNull(primaryRepository, "Property 'primaryRepository' is required");
        this.primaryRepository = primaryRepository;
        this.searchRepository = Objects.requireNonNullElse(searchRepository, primaryRepository);
    }

    @Override
    public Product create(String id) {
        return this.primaryRepository.create(id);
    }

    @Override
    public Product save(Product product) {
        return this.primaryRepository.save(product);
    }

    @Override
    public List<Product> saveAll(Collection<Product> products) {
        return this.primaryRepository.saveAll(products);
    }

    @Override
    public Optional<Product> findById(String id) {
        return this.primaryRepository.findById(id);
    }

    @Override
    public List<Product> findAllById(Collection<String> ids) {
        return this.primaryRepository.findAllById(ids);
    }

    @Override
    public SliceList<Product> findAll(ProductQuery query) {
        return this.searchRepository.findAll(query);
    }

    @Override
    public long count(ProductQuery query) {
        return this.searchRepository.count(query);
    }

    @Override
    public void delete(Product product) {
        this.primaryRepository.delete(product);
    }
}
