package com.mallfoundry.tracking;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TrackingStatus {
    UNKNOWN, // 未知
    PRE_TRANSIT, // 等待拦包
    TRANSIT, //运输中
    DELIVERED, //已送达
    RETURNED, //退回
    FAILURE; // 异常

    @JsonValue
    private String lowercase() {
        return this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return this.lowercase();
    }
}
