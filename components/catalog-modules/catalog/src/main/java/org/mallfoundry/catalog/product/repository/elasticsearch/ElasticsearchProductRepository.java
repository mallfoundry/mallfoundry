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
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductQuery;
import org.mallfoundry.catalog.product.ProductRepository;
import org.mallfoundry.catalog.product.ProductStatus;
import org.mallfoundry.catalog.product.repository.SearchProductRepository;
import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.inventory.InventoryStatus;
import org.mallfoundry.util.CaseUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.util.CastUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ElasticsearchProductRepository implements ProductRepository, SearchProductRepository {

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

    private BoolQueryBuilder createQueryBuilder(ProductQuery productQuery) {
        var queryBuilder = QueryBuilders.boolQuery();

        if (StringUtils.isNotEmpty(productQuery.getName())) {
            queryBuilder.must(QueryBuilders.matchQuery("name", productQuery.getName()));
        }

        if (Objects.nonNull(productQuery.getPriceMin()) || Objects.nonNull(productQuery.getPriceMax())) {
            queryBuilder.must(QueryBuilders.rangeQuery("price").gte(productQuery.getPriceMin()).lte(productQuery.getPriceMax()));
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
        return queryBuilder;
    }

    @Override
    public SliceList<Product> findAll(ProductQuery productQuery) {
        var sort = this.createSort(productQuery);
        var page = PageRequest.of(productQuery.getPage() - 1, productQuery.getLimit(), sort);
        var queryBuilder = this.createQueryBuilder(productQuery);
        var query = new StringQuery(queryBuilder.toString(), page, sort);
        var hits = this.elasticsearchOperations.search(query, ElasticsearchProduct.class);
        var list = hits.map(SearchHit::getContent).toList();
        return CastUtils.cast(PageList.of(list).totalSize(hits.getTotalHits()));
    }

    @Override
    public long count(ProductQuery productQuery) {
        var queryBuilder = this.createQueryBuilder(productQuery);
        var query = new StringQuery(queryBuilder.toString());
        return this.elasticsearchOperations.count(query, ElasticsearchProduct.class);
    }

    @Override
    public void delete(Product product) {
        this.elasticsearchOperations.delete(ElasticsearchProduct.of(product));
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
    public List<Product> saveAll(Collection<Product> products) {
        return CastUtils.cast(
                this.elasticsearchOperations.save(
                        products.stream().map(ElasticsearchProduct::of).collect(Collectors.toList())));
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(this.elasticsearchOperations.get(id, ElasticsearchProduct.class));
    }

    @Override
    public List<Product> findAllById(Collection<String> ids) {
        var query = new CriteriaQuery(new Criteria("id").in(ids));
        var hits = this.elasticsearchOperations.search(query, ElasticsearchProduct.class);
        return CastUtils.cast(hits.map(SearchHit::getContent).toList());
    }
}
