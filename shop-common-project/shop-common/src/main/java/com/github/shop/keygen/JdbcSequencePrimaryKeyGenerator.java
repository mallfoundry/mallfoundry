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

package com.github.shop.keygen;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Objects;

public class JdbcSequencePrimaryKeyGenerator extends AbstractSequencePrimaryKeyGenerator {

    private final JdbcTemplate jdbcTemplate;

    private static RowMapper<NextValue> nextValueRowMapper = BeanPropertyRowMapper.newInstance(NextValue.class);

    private static final String KEY_VALUE_TABLE_NAME = "keyval_sequence";

    private static final String SELECT_KEY_VALUE_SQL = "SELECT key_name_ AS keyName, current_value_ AS currentValue, increment_value_ AS incrementValue FROM " + KEY_VALUE_TABLE_NAME + " WHERE key_name_ = ?";

    private static final String UPDATE_KEY_VALUE_SQL = "UPDATE " + KEY_VALUE_TABLE_NAME + " SET current_value_ = ? WHERE key_name_ = ?";

    private static final String INSERT_KEY_VALUE_SQL = "INSERT INTO " + KEY_VALUE_TABLE_NAME + " (key_name_, current_value_, min_value_, max_value_, increment_value_) VALUES(?, ?, ?, ?, ?)";

    private String selectKeyValueSql = SELECT_KEY_VALUE_SQL;

    private String updateKeyValueSql = UPDATE_KEY_VALUE_SQL;

    private String insertKeyValueSql = INSERT_KEY_VALUE_SQL;

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
    protected Long doNextVal(String keyName) {
        NextValue nextValue = this.getNextValue(keyName);
        if (Objects.isNull(nextValue)) {
            nextValue = new NextValue(keyName);
            this.newNextValue(nextValue);
        } else {
            nextValue.currentValue = nextValue.currentValue + nextValue.incrementValue;
            this.addNextValue(nextValue);
        }
        return nextValue.currentValue;
    }


    public static class NextValue {
        String keyName;
        long currentValue;
        long minValue;
        long maxValue;
        short incrementValue;

        public NextValue() {
        }

        NextValue(String keyName) {
            this.keyName = keyName;
            this.minValue = 1L;
            this.currentValue = this.minValue;
            this.maxValue = 999999999999L;
            this.incrementValue = 1;
        }

        public String getKeyName() {
            return keyName;
        }

        public void setKeyName(String keyName) {
            this.keyName = keyName;
        }

        public long getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(long currentValue) {
            this.currentValue = currentValue;
        }

        public long getMinValue() {
            return minValue;
        }

        public void setMinValue(long minValue) {
            this.minValue = minValue;
        }

        public long getMaxValue() {
            return maxValue;
        }

        public void setMaxValue(long maxValue) {
            this.maxValue = maxValue;
        }

        public short getIncrementValue() {
            return incrementValue;
        }

        public void setIncrementValue(short incrementValue) {
            this.incrementValue = incrementValue;
        }
    }
}
