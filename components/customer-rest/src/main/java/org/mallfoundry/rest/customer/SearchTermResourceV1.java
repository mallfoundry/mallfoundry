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
    public SearchTermResponse addTerm(@PathVariable("customer_id") String customerId,
                                      @RequestBody String term) {
        return new SearchTermResponse(this.searchTermService.addTerm(customerId, term));
    }

    @Operation(description = "获得顾客的搜索词条对象集合")
    @GetMapping("/customers/{customer_id}/search-terms")
    public List<SearchTermResponse> getTerms(@PathVariable("customer_id") String customerId) {
        var terms = this.searchTermService.getTerms(customerId);
        return CollectionUtils.isEmpty(terms)
                ? Collections.emptyList()
                : terms.stream().map(SearchTermResponse::new).collect(Collectors.toUnmodifiableList());
    }

    @Operation(description = "删除指定顾客的搜索词条对象")
    @DeleteMapping("/customers/{customer_id}/search-terms/{term}")
    public void deleteTerm(@PathVariable("customer_id") String customerId,
                           @PathVariable("term") String term) {
        this.searchTermService.deleteTerm(customerId, term);
    }

    @Operation(description = "批量删除顾客的搜索词条对象集合")
    @DeleteMapping("/customers/{customer_id}/search-terms/batch")
    public void deleteTerms(@PathVariable("customer_id") String customerId,
                            @RequestBody List<String> terms) {
        this.searchTermService.deleteTerms(customerId, terms);
    }

    @Operation(description = "清空顾客的搜索词条对象集合")
    @DeleteMapping("/customers/{customer_id}/search-terms/clear")
    public void clearTerms(@PathVariable("customer_id") String customerId) {
        this.searchTermService.clearTerms(customerId);
    }

}
