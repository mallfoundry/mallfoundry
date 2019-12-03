/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mallfoundry.access.infrastructure.persistence.mybatis.token;

import com.mallfoundry.access.domain.token.AccessToken;
import com.mallfoundry.access.domain.token.AccessTokenRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AccessTokenRepositoryMybatis implements AccessTokenRepository {

    private AccessTokenMapper accessTokenMapper;

    public AccessTokenRepositoryMybatis(AccessTokenMapper accessTokenMapper) {
        this.accessTokenMapper = accessTokenMapper;
    }

    @Override
    public void add(AccessToken token) {
        this.accessTokenMapper.insert(token);
    }

    @Override
    public void deleteByUsername(String username) {
        this.accessTokenMapper.deleteByUsername(username);
    }

    @Override
    public void deleteByToken(String tokenValue) {
        this.accessTokenMapper.deleteByToken(tokenValue);
    }

    @Override
    public AccessToken findByToken(String tokenValue) {
        return this.accessTokenMapper.selectByToken(tokenValue);
    }

    @Override
    public AccessToken findByUsername(String username) {
        return this.accessTokenMapper.selectByUsername(username);
    }
}
