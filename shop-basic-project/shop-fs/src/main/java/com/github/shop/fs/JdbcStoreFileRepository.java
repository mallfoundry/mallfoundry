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

package com.github.shop.fs;

import com.github.shop.fs.store.StoreFileInfo;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class JdbcStoreFileRepository implements StoreFileRepository {

    private final String saveFileInfoSQL = "INSERT INTO store_file_name (path_, store_path_) VALUES (?,?)";

    private final String findFileInfoSQL = "SELECT path_ AS path, store_path_ as store_path from store_file_name where path_ = ?";

    private final RowMapper<StoreFileInfo> fileInfoRowMapper = new BeanPropertyRowMapper<>(StoreFileInfo.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcStoreFileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(StoreFileInfo info) {
        this.jdbcTemplate.update(this.saveFileInfoSQL, info.getPath(), info.getStorePath());
    }

    @Override
    public void delete(String fid) {

    }

    @Override
    public StoreFileInfo find(String fid) {
        List<StoreFileInfo> files = this.jdbcTemplate.query(findFileInfoSQL, fileInfoRowMapper);
        return DataAccessUtils.singleResult(files);
    }
}
