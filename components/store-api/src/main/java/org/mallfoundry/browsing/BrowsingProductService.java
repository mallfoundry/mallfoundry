package org.mallfoundry.browsing;

import org.mallfoundry.data.SliceList;

import java.util.List;

public interface BrowsingProductService {

    BrowsingProduct createBrowsingProduct(String browserId, String productId);

    BrowsingProductQuery createBrowsingProductQuery();

    BrowsingProduct addBrowsingProduct(String browserId, String productId);

    SliceList<BrowsingProduct> getBrowsingProducts(BrowsingProductQuery query);

    long getBrowsingProductCount(BrowsingProductQuery query);

    void deleteBrowsingProduct(String browserId, String productId);

    void deleteBrowsingProducts(String browserId, List<String> productIds);
}
