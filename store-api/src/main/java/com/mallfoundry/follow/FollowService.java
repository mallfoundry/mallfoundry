package com.mallfoundry.follow;

import com.mallfoundry.data.SliceList;

public interface FollowService {

    FollowerId createFollowerId(String id);

    FollowProductQuery createFollowProductQuery();

    FollowStoreQuery createFollowStoreQuery();

    FollowProduct followingProduct(String followerId, String productId);

    void unfollowingProduct(String followerId, String productId);

    boolean checkFollowingProduct(String followerId, String productId);

    SliceList<FollowProduct> getFollowingProducts(FollowProductQuery query);

    long getFollowingProductCount(FollowProductQuery query);

    long getProductFollowerCount(String productId);

    void followingStore(String followerId, String storeId);

    void unfollowingStore(String followerId, String storeId);

    boolean checkFollowingStore(String followerId, String storeId);

    SliceList<FollowStore> getFollowingStores(FollowStoreQuery query);

    long getFollowingStoreCount(FollowStoreQuery query);

    long getStoreFollowerCount(String storeId);
}
