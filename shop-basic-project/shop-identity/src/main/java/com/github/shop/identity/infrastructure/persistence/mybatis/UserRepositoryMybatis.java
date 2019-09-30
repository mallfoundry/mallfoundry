package com.github.shop.identity.infrastructure.persistence.mybatis;

import com.github.shop.identity.User;
import com.github.shop.identity.UserRepository;
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
