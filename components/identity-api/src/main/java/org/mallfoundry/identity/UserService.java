package org.mallfoundry.identity;

import java.util.Optional;

public interface UserService {

    UserId createUserId(String userId);

    User createUser(String id);

    User createUser(UserRegistration registration) throws UserException;

    void deleteUser(String username);

    void changePassword(String username, String password, String originalPassword) throws UserException;

    void resetPassword(String username, String password) throws UserException;

    User setUser(User user);

    Optional<User> getUser(String userId);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByMobile(String countryCode, String mobile);

}
