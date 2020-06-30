package org.mallfoundry.identity;

import java.util.Optional;

public interface UserService {

    UserId createUserId(String userId);

    User createUser(String id);

    User createUser(UserRegistration registration) throws UserException;

    void deleteUser(String id);

    void changePassword(String id, String password, String originalPassword) throws UserException;

    void resetPassword(String id, String password) throws UserException;

    User setUser(User user);

    Optional<User> getUser(String userId);

    Optional<User> getUserByUsername(String id);

    Optional<User> getUserByMobile(String countryCode, String mobile);

}
