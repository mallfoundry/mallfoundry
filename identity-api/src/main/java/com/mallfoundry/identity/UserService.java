package com.mallfoundry.identity;

import java.util.Optional;

public interface UserService {

    UserId createUserId(String userId);

    User createUser(String username, String password);

    void deleteUser(String username);

    void changePassword(String username, String password, String originalPassword) throws UserException;

    User saveUser(User user);

    Optional<User> getUser(String username);

    Optional<User> getUser(UserId userId);

}
