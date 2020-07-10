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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductQuery;
import org.mallfoundry.catalog.product.ProductRepository;
import org.mallfoundry.catalog.product.ProductStatus;
import org.mallfoundry.catalog.product.SearchProductRepository;
import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.inventory.InventoryStatus;
import org.mallfoundry.util.CaseUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.util.CastUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ElasticsearchProductRepository
        implements ProductRepository, SearchProductRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    public ElasticsearchProductRepository(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    private Sort createSort(ProductQuery query) {
        return Optional.ofNullable(query.getSort())
                .map(aSort -> Sort.by(aSort.getOrders().stream()
                        .peek(sortOrder -> sortOrder.setProperty(CaseUtils.camelCase(sortOrder.getProperty())))
                        .map(sortOrder -> sortOrder.getDirection().isDescending()
                                ? Sort.Order.desc(sortOrder.getProperty())
                                : Sort.Order.asc(sortOrder.getProperty()))
                        .collect(Collectors.toUnmodifiableList())))
                .orElseGet(Sort::unsorted);
    }

    @Override
    public SliceList<Product> findAll(ProductQuery productQuery) {
        var sort = this.createSort(productQuery);
        var page = PageRequest.of(productQuery.getPage() - 1, productQuery.getLimit(), sort);
        var queryBuilder = QueryBuilders.boolQuery();

        if (StringUtils.isNotEmpty(productQuery.getName())) {
            queryBuilder.must(QueryBuilders.matchQuery("name", productQuery.getName()));
        }

        if (Objects.nonNull(productQuery.getMinPrice()) || Objects.nonNull(productQuery.getMaxPrice())) {
            queryBuilder.must(QueryBuilders.rangeQuery("price").gte(productQuery.getMinPrice()).lte(productQuery.getMaxPrice()));
        }

        if (StringUtils.isNotEmpty(productQuery.getStoreId())) {
            queryBuilder.must(QueryBuilders.termQuery("storeId", productQuery.getStoreId()));
        }

        if (CollectionUtils.isNotEmpty(productQuery.getStatuses())) {
            var statuses = productQuery.getStatuses().stream()
                    .map(ProductStatus::name).collect(Collectors.toUnmodifiableSet());
            queryBuilder.must(QueryBuilders.termsQuery("status", statuses));
        }

        if (CollectionUtils.isNotEmpty(productQuery.getInventoryStatuses())) {
            var inventoryStatuses = productQuery.getInventoryStatuses().stream()
                    .map(InventoryStatus::name).collect(Collectors.toUnmodifiableSet());
            queryBuilder.must(QueryBuilders.termsQuery("inventoryStatus", inventoryStatuses));
        }

        if (CollectionUtils.isNotEmpty(productQuery.getCollections())) {
            queryBuilder.must(QueryBuilders.termsQuery("collections", productQuery.getCollections()));
        }

        var query = new StringQuery(queryBuilder.toString(), page, sort);
        var hits = elasticsearchOperations.search(query, ElasticsearchProduct.class);
        var list = hits.map(SearchHit::getContent).toList();

        return CastUtils.cast(PageList.of(list).totalSize(hits.getTotalHits()).page(productQuery.getPage()).limit(productQuery.getLimit()));
    }

    @Override
    public Product create(String id) {
        return new ElasticsearchProduct(id);
    }

    @Override
    public Product save(Product product) {
        return this.elasticsearchOperations.save(ElasticsearchProduct.of(product));
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(this.elasticsearchOperations.get(id, ElasticsearchProduct.class));
    }
}
