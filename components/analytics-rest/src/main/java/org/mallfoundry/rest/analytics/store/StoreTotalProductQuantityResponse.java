package org.mallfoundry.rest.analytics.store;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.analytics.store.StoreTotalProductQuantity;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class StoreTotalProductQuantityResponse {
    private int activeQuantity;
    private int archivedQuantity;
    private int pendingQuantity;
    private int disapprovedQuantity;

    public static StoreTotalProductQuantityResponse of(StoreTotalProductQuantity quantity) {
        var response = new StoreTotalProductQuantityResponse();
        BeanUtils.copyProperties(quantity, response);
        return response;
    }
}
