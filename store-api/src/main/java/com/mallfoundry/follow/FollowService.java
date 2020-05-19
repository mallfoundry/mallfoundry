package com.mallfoundry.follow;

import com.mallfoundry.data.SliceList;
import com.mallfoundry.store.StoreId;

public interface FollowService {

    FollowerId createFollowerId(String id);

    FollowProductQuery createFollowProductQuery();

    FollowStoreQuery createFollowStoreQuery();

    FollowProduct followingProduct(String productId);

    void unfollowingProduct(String productId);

    boolean checkFollowingProduct(String productId);

    SliceList<FollowProduct> getFollowingProducts(FollowProductQuery query);

    long getFollowingProductCount(FollowProductQuery query);

    void followingStore(FollowerId followerId, StoreId storeId);

    void unfollowingStore(FollowerId followerId, StoreId storeId);

    boolean checkFollowingStore(FollowerId followerId, StoreId storeId);

    SliceList<FollowStore> getFollowingStores(FollowStoreQuery query);

    long getFollowingStoreCount(FollowStoreQuery query);
}
