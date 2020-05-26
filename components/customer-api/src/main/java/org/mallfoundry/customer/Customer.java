package org.mallfoundry.customer;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface Customer {

    String getId();

    String getUserId();

    String getAvatar();

    void setAvatar(String avatar);

    String getNickname();

    void setNickname(String nickname);

    Gender getGender();

    void setGender(Gender gender);

    Date getBirthday();

    void setBirthday(Date birthday);

    List<ShippingAddress> getShippingAddresses();

    void addShippingAddress(ShippingAddress shippingAddress);

    void removeShippingAddress(ShippingAddress shippingAddress);

    Optional<ShippingAddress> getDefaultShippingAddress();

    Optional<ShippingAddress> getShippingAddress(String id);

}
