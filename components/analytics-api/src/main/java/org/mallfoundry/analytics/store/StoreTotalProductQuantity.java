package org.mallfoundry.analytics.store;

public interface StoreTotalProductQuantity {

    int getActiveQuantity();

    int getArchivedQuantity();

    int getPendingQuantity();

    int getDisapprovedQuantity();
}
