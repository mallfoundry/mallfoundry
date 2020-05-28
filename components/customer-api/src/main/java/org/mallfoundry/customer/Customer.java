package org.mallfoundry.customer;

import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface Customer {

    String getId();

    String getUserId();

    void setUserId(String userId);

    String getAvatar();

    void setAvatar(String avatar);

    String getNickname();

    void setNickname(String nickname);

    Gender getGender();

    void setGender(Gender gender);

    Date getBirthday();

    void setBirthday(Date birthday);

    ShippingAddress createAddress(String id);

    List<ShippingAddress> getAddresses();

    Optional<ShippingAddress> getDefaultAddress();

    Optional<ShippingAddress> getAddress(String id);

    void addAddress(ShippingAddress shippingAddress);

    void setAddress(ShippingAddress shippingAddress);

    void removeAddress(ShippingAddress shippingAddress);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<Customer> {

        Builder userId(String userId);

        Builder avatar(String avatar);

        Builder nickname(String nickname);

        Builder gender(Gender gender);

        Builder birthday(Date birthday);
    }

    class BuilderSupport implements Builder {
        private final Customer customer;

        public BuilderSupport(Customer customer) {
            this.customer = customer;
        }

        @Override
        public Builder userId(String userId) {
            this.customer.setUserId(userId);
            return this;
        }

        @Override
        public Builder avatar(String avatar) {
            this.customer.setAvatar(avatar);
            return this;
        }

        @Override
        public Builder nickname(String nickname) {
            this.customer.setNickname(nickname);
            return this;
        }

        @Override
        public Builder gender(Gender gender) {
            this.customer.setGender(gender);
            return this;
        }

        @Override
        public Builder birthday(Date birthday) {
            this.customer.setBirthday(birthday);
            return this;
        }

        @Override
        public Customer build() {
            return this.customer;
        }
    }

}
