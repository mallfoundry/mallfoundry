package org.mallfoundry.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface Subject extends UserDetails {

    String getId();

    String getUsername();

    String getNickname();
}
