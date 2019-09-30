package com.mallfoundry.identity;

/**
 * A identity service.
 *
 * @author Zhi Tang
 */
public interface IdentityService {

    /**
     * Create a user.
     *
     * @param user a user
     */
    void createUser(User user);

    /**
     * Delete the user based on the username.
     *
     * @param username username
     */
    void deleteUser(String username);

    /**
     * Update user information based on username
     *
     * @param user a user
     */
    void updateUser(User user);

    User getUser(String username);
}
