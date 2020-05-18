package com.mallfoundry.browsing;

import com.mallfoundry.data.SliceList;

import java.util.List;

public interface BrowsingProductService {

    BrowsingProduct createBrowsingProduct(String customerId, String productId);

    BrowsingProductQuery createBrowsingProductQuery();

    BrowsingProduct saveBrowsingProduct(BrowsingProduct browsingProduct);

    void deleteBrowsingProduct(String id);

    void deleteBrowsingProducts(List<String> ids);

    SliceList<BrowsingProduct> getBrowsingProducts(BrowsingProductQuery query);

    long getBrowsingProductCount(BrowsingProductQuery query);
}
