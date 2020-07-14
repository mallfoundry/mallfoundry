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

package org.mallfoundry.catalog.product;

import org.mallfoundry.data.SliceList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryDelegate implements ProductRepository {

    private final JdbcProductRepository jdbcProductRepository;

    private final SearchProductRepository searchProductRepository;

    public ProductRepositoryDelegate(JdbcProductRepository jdbcProductRepository,
                                     SearchProductRepository searchProductRepository) {
        this.jdbcProductRepository = jdbcProductRepository;
        this.searchProductRepository = searchProductRepository;
    }

    @Override
    public Product create(String id) {
        return this.jdbcProductRepository.create(id);
    }

    @Override
    public Product save(Product product) {
        this.searchProductRepository.save(product);
        return this.jdbcProductRepository.save(product);
    }

    @Override
    public List<Product> saveAll(Collection<Product> products) {
        this.searchProductRepository.saveAll(products);
        return this.jdbcProductRepository.saveAll(products);
    }

    @Override
    public Optional<Product> findById(String id) {
        return this.jdbcProductRepository.findById(id);
    }

    @Override
    public List<Product> findAllById(Collection<String> ids) {
        return this.jdbcProductRepository.findAllById(ids);
    }

    @Override
    public SliceList<Product> findAll(ProductQuery query) {
        return this.searchProductRepository.findAll(query);
    }
}
