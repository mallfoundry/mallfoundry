package com.mallfoundry.identity;

public interface UserRepository {

    void save(User user);

    void update(User user);

    void delete(String username);

    User findByUsername(String username);
}
