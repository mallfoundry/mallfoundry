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

package org.mallfoundry.rest.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.customer.SearchTermService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Customer Search Term Resource V1", description = "顾客搜索词条资源")
@RestController
@RequestMapping("/v1")
public class SearchTermResourceV1 {

    private final SearchTermService searchTermService;

    public SearchTermResourceV1(SearchTermService searchTermService) {
        this.searchTermService = searchTermService;
    }

    @Operation(description = "添加顾客的一个搜索词条对象")
    @PostMapping("/customers/{customer_id}/search-terms")
    public SearchTermResponse addSearchTerm(@PathVariable("customer_id") String customerId,
                                      @RequestBody String term) {
        return new SearchTermResponse(this.searchTermService.addSearchTerm(customerId, term));
    }

    @Operation(description = "获得顾客的搜索词条对象集合")
    @GetMapping("/customers/{customer_id}/search-terms")
    public List<SearchTermResponse> getSearchTerms(@PathVariable("customer_id") String customerId) {
        var terms = this.searchTermService.getSearchTerms(customerId);
        return CollectionUtils.isEmpty(terms)
                ? Collections.emptyList()
                : terms.stream().map(SearchTermResponse::new).collect(Collectors.toUnmodifiableList());
    }

    @Operation(description = "删除指定顾客的搜索词条对象")
    @DeleteMapping("/customers/{customer_id}/search-terms/{term}")
    public void deleteSearchTerm(@PathVariable("customer_id") String customerId,
                           @PathVariable("term") String term) {
        this.searchTermService.deleteSearchTerm(customerId, term);
    }

    @Operation(description = "批量删除顾客的搜索词条对象集合")
    @DeleteMapping("/customers/{customer_id}/search-terms/batch")
    public void deleteSearchTerms(@PathVariable("customer_id") String customerId,
                            @RequestBody List<String> terms) {
        this.searchTermService.deleteSearchTerms(customerId, terms);
    }

    @Operation(description = "清空顾客的搜索词条对象集合")
    @DeleteMapping("/customers/{customer_id}/search-terms/clear")
    public void clearSearchTerms(@PathVariable("customer_id") String customerId) {
        this.searchTermService.clearSearchTerms(customerId);
    }

}
