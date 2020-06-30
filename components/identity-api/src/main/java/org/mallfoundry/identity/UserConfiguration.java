package org.mallfoundry.identity;

public interface UserConfiguration {

    String getDefaultUsername();

    void setDefaultUsername(String defaultUsername);

    String getDefaultNickname();

    void setDefaultNickname(String defaultNickname);
}
