package org.mallfoundry.customer;

import org.mallfoundry.data.SliceList;

import java.util.List;

public interface BrowsingProductService {

    BrowsingProduct createBrowsingProduct(String id);

    BrowsingProductQuery createBrowsingProductQuery();

    BrowsingProduct addBrowsingProduct(BrowsingProduct browsingProduct);

    SliceList<BrowsingProduct> getBrowsingProducts(BrowsingProductQuery query);

    long getBrowsingProductCount(BrowsingProductQuery query);

    void deleteBrowsingProduct(String browserId, String id);

    void deleteBrowsingProducts(String browserId, List<String> ids);
}
