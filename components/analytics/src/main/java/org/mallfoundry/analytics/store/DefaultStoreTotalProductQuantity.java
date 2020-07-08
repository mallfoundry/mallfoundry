package org.mallfoundry.analytics.store;

import lombok.Getter;

@Getter
public class DefaultStoreTotalProductQuantity implements StoreTotalProductQuantity {
    private int activeQuantity;
    private int archivedQuantity;
    private int pendingQuantity;
    private int disapprovedQuantity;
}
