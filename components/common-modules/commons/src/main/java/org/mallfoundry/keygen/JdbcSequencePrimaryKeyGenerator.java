/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.keygen;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Objects;

public class JdbcSequencePrimaryKeyGenerator extends AbstractSequencePrimaryKeyGenerator {

    private final JdbcTemplate jdbcTemplate;

    private static final String KEY_VALUE_TABLE_NAME = "mf_keyval_sequence";

    private static final String SELECT_KEY_VALUE_SQL = "SELECT key_name_ AS keyName, current_value_ AS currentValue, increment_value_ AS incrementValue FROM " + KEY_VALUE_TABLE_NAME + " WHERE key_name_ = ?";

    private static final String UPDATE_KEY_VALUE_SQL = "UPDATE " + KEY_VALUE_TABLE_NAME + " SET current_value_ = ? WHERE key_name_ = ?";

    private static final String INSERT_KEY_VALUE_SQL = "INSERT INTO " + KEY_VALUE_TABLE_NAME + " (key_name_, current_value_, min_value_, max_value_, increment_value_) VALUES(?, ?, ?, ?, ?)";

    @Setter
    private String selectKeyValueSql = SELECT_KEY_VALUE_SQL;

    @Setter
    private String updateKeyValueSql = UPDATE_KEY_VALUE_SQL;

    @Setter
    private String insertKeyValueSql = INSERT_KEY_VALUE_SQL;

    private final RowMapper<NextValue> nextValueRowMapper = BeanPropertyRowMapper.newInstance(NextValue.class);

    public JdbcSequencePrimaryKeyGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private NextValue getNextValue(String keyName) {
        return DataAccessUtils.singleResult(this.jdbcTemplate.query(selectKeyValueSql, nextValueRowMapper, keyName));
    }

    private void addNextValue(NextValue nextValue) {
        this.jdbcTemplate.update(updateKeyValueSql, nextValue.currentValue, nextValue.keyName);
    }

    private void newNextValue(NextValue nextValue) {
        this.jdbcTemplate.update(
                insertKeyValueSql, nextValue.keyName, nextValue.currentValue,
                nextValue.minValue, nextValue.maxValue, nextValue.incrementValue);
    }

    @Override
    protected Long doNext(String key) {
        NextValue nextValue = this.getNextValue(key);
        if (Objects.isNull(nextValue)) {
            nextValue = new NextValue(key);
            this.newNextValue(nextValue);
        } else {
            nextValue.currentValue = nextValue.currentValue + nextValue.incrementValue;
            this.addNextValue(nextValue);
        }
        return nextValue.currentValue;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class NextValue {

        String keyName;
        long currentValue;
        long minValue;
        long maxValue;
        short incrementValue;

        NextValue(String keyName) {
            this.keyName = keyName;
            this.minValue = 1L;
            this.currentValue = this.minValue;
            this.maxValue = Long.MAX_VALUE;
            this.incrementValue = 1;
        }
    }
}
