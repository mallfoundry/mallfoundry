package org.mallfoundry.identity;

import java.util.Map;

public interface UserRegistration {

    String getUsername();

    String getPassword();

    String getNickname();

    String getMobile();

    String getMail();

    Mode getMode();

    Map<String, String> getParameters();

    void setParameters(Map<String, String> parameters);

    void setParameter(String name, String value);

    String getParameter(String name);

    void assignToUser(User user);

    enum Mode {
        MAIL, MOBILE;
    }
}
