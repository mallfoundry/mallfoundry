package com.mallfoundry.identity;

import java.util.Optional;

public interface UserService {

    User createUser(String username, String password);

    Optional<User> getUser(String username);

    // void changePassword(String username, String password, String oldPassword);

}
