package org.mallfoundry.customer;

import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface Customer {

    String getId();

    String getUsername();

    void setUsername(String username);

    String getAvatar();

    void setAvatar(String avatar);

    String getNickname();

    void setNickname(String nickname);

    Gender getGender();

    void setGender(Gender gender);

    Date getBirthday();

    void setBirthday(Date birthday);

    CustomerAddress createAddress(String id);

    List<CustomerAddress> getAddresses();

    Optional<CustomerAddress> getDefaultAddress();

    Optional<CustomerAddress> getAddress(String id);

    void addAddress(CustomerAddress address);

    void setAddress(CustomerAddress address);

    void removeAddress(CustomerAddress address);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<Customer> {

        Builder username(String username);

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
        public Builder username(String username) {
            this.customer.setUsername(username);
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
