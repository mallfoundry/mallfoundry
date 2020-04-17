package com.mallfoundry.shipping;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum CarrierCode implements Serializable {
    SF, // 顺丰
    ZTO, // 中通
    STO, // 申通
    YTO; // 圆通

    @JsonValue
    private String lowercase() {
        return this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return this.lowercase();
    }
}
