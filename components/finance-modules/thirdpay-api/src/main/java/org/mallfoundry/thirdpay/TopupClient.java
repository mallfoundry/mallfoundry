package org.mallfoundry.thirdpay;

import org.mallfoundry.finance.Topup;
import org.mallfoundry.finance.TopupException;
import org.mallfoundry.finance.TopupNotification;

public interface TopupClient {

    boolean supportsTopup(Topup topup);

    Topup createTopup(Topup topup) throws TopupException;

    TopupNotification validateNotification(Object parameters);
}
