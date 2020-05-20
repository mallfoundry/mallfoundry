package com.mallfoundry.follow;

import com.mallfoundry.data.SliceList;

public interface FollowService {

    FollowerId createFollowerId(String id);

    FollowProductQuery createFollowProductQuery();

    FollowStoreQuery createFollowStoreQuery();

    FollowProduct followingProduct(String productId);

    void unfollowingProduct(String productId);

    boolean checkFollowingProduct(String productId);

    SliceList<FollowProduct> getFollowingProducts(FollowProductQuery query);

    long getFollowingProductCount(FollowProductQuery query);

    long getProductFollowerCount(String productId);

    void followingStore(String storeId);

    void unfollowingStore(String storeId);

    boolean checkFollowingStore(String storeId);

    SliceList<FollowStore> getFollowingStores(FollowStoreQuery query);

    long getFollowingStoreCount(FollowStoreQuery query);

    long getStoreFollowerCount(String storeId);
}
