package org.mallfoundry.discount;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface Coupon {

    String getId();

    String getStoreId();

    void setStoreId(String storeId);

    String getName();

    void setName(String name);

    String getCode();

    void setCode(String code);

    CouponStatus getStatus();

    int getUses();

    int getMaxUses();

    void setMaxUses(int maxUses);

    int getMaxUsesPerCustomer();

    void setMaxUsesPerCustomer(int maxUsesPerCustomer);

    UsesLimit getUsesLimit();

    void setUsesLimit(UsesLimit limit);

    int getExpires();

    void setExpires(int expires);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    BigDecimal getMinAmount();

    void setMinAmount(BigDecimal minAmount);

    BigDecimal getMaxAmount();

    void setMaxAmount(BigDecimal maxAmount);

    List<String> getProducts();

    void setProducts(List<String> products);

    List<String> getExcludedProducts();

    void setExcludedProducts(List<String> products);

    List<String> getCollections();

    void setCollections(List<String> collections);

    List<String> getExcludedCollections();

    void setExcludedCollections(List<String> collections);

    Date getCreatedTime();

//    BigDecimal calculateDiscountAmount();
}
