package org.mallfoundry.payment.methods;

import org.mallfoundry.util.Position;

public interface PaymentMethod extends Position {

    String getCode();

    void setCode(String code);

    String getName();

    void setName(String name);

    String getLogo();

    void setLogo(String logo);

    String getColor();

    void setColor(String color);

    String getDescription();

    void setDescription(String description);

    boolean isEnabled();

    void setEnabled(boolean enabled);
}
