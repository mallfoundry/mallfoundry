package org.mallfoundry.identity;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;

public interface UserRegistration {

    String getPassword();

    void setPassword(String password);

    String getNickname();

    void setNickname(String nickname);

    String getMobile();

    String getEmail();

    Mode getMode();

    Map<String, String> getParameters();

    void setParameters(Map<String, String> parameters);

    void setParameter(String name, String value);

    String getParameter(String name);

    User assignToUser(User user);

    enum Mode {
        EMAIL, MOBILE;

        @JsonValue
        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
