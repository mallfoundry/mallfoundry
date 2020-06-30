package org.mallfoundry.identity;

import java.util.Optional;

public interface UserRepository {

    User create(String id);

    User save(User user);

    void delete(User user);

    Optional<User> findById(String id);

    Optional<User> findByMobile(String mobile);

    Optional<User> findByUsername(String username);
}
