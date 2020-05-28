package org.mallfoundry.rest.customer;


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

@RestController
@RequestMapping("/v1")
public class SearchTermResourceV1 {

    private final SearchTermService searchTermService;

    public SearchTermResourceV1(SearchTermService searchTermService) {
        this.searchTermService = searchTermService;
    }

    @PostMapping("/customers/{customer_id}/search-terms")
    public void addTerm(@PathVariable("customer_id") String customerId,
                        @RequestBody String term) {
        this.searchTermService.addTerm(customerId, term);
    }

    @GetMapping("/customers/{customer_id}/search-terms")
    public List<SearchTermResponse> getTerms(@PathVariable("customer_id") String customerId) {
        var terms = this.searchTermService.getTerms(customerId);
        return CollectionUtils.isEmpty(terms)
                ? Collections.emptyList()
                : terms.stream().map(SearchTermResponse::new).collect(Collectors.toUnmodifiableList());
    }

    @DeleteMapping("/customers/{customer_id}/search-terms/{term}")
    public void deleteTerm(@PathVariable("customer_id") String customerId,
                           @PathVariable("term") String term) {
        this.searchTermService.deleteTerm(customerId, term);
    }

    @DeleteMapping("/customers/{customer_id}/search-terms/batch")
    public void deleteTerms(@PathVariable("customer_id") String customerId,
                            @RequestBody List<String> terms) {
        this.searchTermService.deleteTerms(customerId, terms);
    }

    @DeleteMapping("/customers/{customer_id}/search-terms/clear")
    public void clearTerms(@PathVariable("customer_id") String customerId) {
        this.searchTermService.clearTerms(customerId);
    }

}
