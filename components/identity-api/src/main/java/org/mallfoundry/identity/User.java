package org.mallfoundry.identity;

import org.mallfoundry.util.ObjectBuilder;

import java.util.List;

public interface User {

    String getId();

    String getUsername();

    void setUsername(String username);

    String getNickname();

    void setNickname(String nickname);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getMobile();

    void setMobile(String mobile);

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void changePassword(String password);

    List<String> getAuthorities();

    void setAuthorities(List<String> authorities);

    default Builder toBuilder() {
        return new BuilderSupport(this);
    }

    interface Builder extends ObjectBuilder<User> {

        Builder username(String username);

        Builder nickname(String nickname);

        Builder countryCode(String countryCode);

        Builder mobile(String mobile);

        Builder email(String mail);

        Builder password(String password);

        Builder authorities(List<String> authorities);
    }

    class BuilderSupport implements Builder {
        private final User user;

        public BuilderSupport(User user) {
            this.user = user;
        }

        @Override
        public Builder username(String username) {
            this.user.setUsername(username);
            return this;
        }

        @Override
        public Builder nickname(String nickname) {
            this.user.setNickname(nickname);
            return this;
        }

        @Override
        public Builder countryCode(String countryCode) {
            this.user.setCountryCode(countryCode);
            return this;
        }

        @Override
        public Builder mobile(String mobile) {
            this.user.setMobile(mobile);
            return this;
        }

        @Override
        public Builder email(String email) {
            this.user.setEmail(email);
            return this;
        }

        @Override
        public Builder password(String password) {
            this.user.changePassword(password);
            return this;
        }

        @Override
        public Builder authorities(List<String> authorities) {
            this.user.setAuthorities(authorities);
            return this;
        }

        @Override
        public User build() {
            return this.user;
        }
    }
}
