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

package org.mallfoundry.catalog.product.repository.elasticsearch;

import org.mallfoundry.catalog.product.ProductDeletedEvent;
import org.mallfoundry.catalog.product.ProductEvent;
import org.springframework.context.event.EventListener;

public class ElasticsearchProductPersistEventListener {

    private final ElasticsearchProductRepository repository;

    public ElasticsearchProductPersistEventListener(ElasticsearchProductRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void handleProduct(ProductEvent event) {
        if (event instanceof ProductDeletedEvent) {
            this.repository.delete(event.getProduct());
        } else {
            this.repository.save(event.getProduct());
        }
    }
}
