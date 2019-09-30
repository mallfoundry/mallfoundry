package com.mallfoundry.identity.infrastructure.persistence.mybatis;

import com.mallfoundry.identity.User;
import com.mallfoundry.identity.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryMybatis implements UserRepository {

    @Override
    public void save(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(String username) {

    }

    @Override
    public User findByUsername(String username) {
        return null;
    }
}
