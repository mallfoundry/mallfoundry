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

public interface ProductRepository {

    Product create(String id);

    Product save(Product product);

    List<Product> saveAll(Collection<Product> products);

    Optional<Product> findById(String id);

    List<Product> findAllById(Collection<String> ids);

    SliceList<Product> findAll(ProductQuery query);
}
