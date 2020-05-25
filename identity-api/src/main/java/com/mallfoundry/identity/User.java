package com.mallfoundry.identity;

import java.util.List;

public interface User {

    String getId();

    String getUsername();

    String getNickname();

    void setNickname(String nickname);

//    String getMobile();

    String getPassword();

    void changePassword(String password);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    List<String> getAuthorities();

}
