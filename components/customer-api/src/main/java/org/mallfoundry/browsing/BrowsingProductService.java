package org.mallfoundry.browsing;

import org.mallfoundry.data.SliceList;

import java.util.List;

public interface BrowsingProductService {

    BrowsingProduct createBrowsingProduct();

    BrowsingProductQuery createBrowsingProductQuery();

    BrowsingProduct addBrowsingProduct(BrowsingProduct browsingProduct);

    void deleteBrowsingProduct(String id);

    void deleteBrowsingProducts(List<String> ids);

    SliceList<BrowsingProduct> getBrowsingProducts(BrowsingProductQuery query);

    long getBrowsingProductCount(BrowsingProductQuery query);
}
