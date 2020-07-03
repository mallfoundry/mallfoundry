package org.mallfoundry.follow;

import org.mallfoundry.data.SliceList;

public interface FollowService {

    FollowerId createFollowerId(String id);

    FollowProductQuery createFollowProductQuery();

    FollowStoreQuery createFollowStoreQuery();

    FollowProduct followProduct(String followerId, String productId);

    void unfollowProduct(String followerId, String productId);

    boolean checkFollowingProduct(String followerId, String productId);

    SliceList<FollowProduct> getFollowingProducts(FollowProductQuery query);

    long getFollowingProductCount(FollowProductQuery query);

    long getProductFollowerCount(String productId);

    void followStore(String followerId, String storeId);

    void unfollowStore(String followerId, String storeId);

    boolean checkFollowingStore(String followerId, String storeId);

    SliceList<FollowStore> getFollowingStores(FollowStoreQuery query);

    long getFollowingStoreCount(FollowStoreQuery query);

    long getStoreFollowerCount(String storeId);
}
