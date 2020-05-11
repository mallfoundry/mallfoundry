package com.mallfoundry.store.product;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductStatus {
    ACTIVE, // 销售中
    ARCHIVED, // 仓库中
    PENDING, // 等待批准
    DISAPPROVED; // 未批准

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
