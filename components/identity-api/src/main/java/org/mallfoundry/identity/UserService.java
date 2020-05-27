package org.mallfoundry.identity;

import java.util.Optional;

public interface UserService {

    UserId createUserId(String userId);

    User createUser(UserRegistration registration) throws UserException;

    void deleteUser(String username);

    void changePassword(String username, String password, String originalPassword) throws UserException;

    User setUser(User user);

//    void login();

    Optional<User> getUser(String username);

    Optional<User> getUser(UserId userId);

}
