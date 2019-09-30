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

package com.mallfoundry.catalog.infrastructure.persistence.mybatis;

import com.mallfoundry.catalog.ProductSpecification;
import com.mallfoundry.util.JsonUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class ProductSpecificationsTypeHandler extends BaseTypeHandler<List<ProductSpecification>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<ProductSpecification> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JsonUtils.stringify(parameter));
    }

    @Override
    public List<ProductSpecification> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.getNullableResult(rs.getString(columnName));
    }

    @Override
    public List<ProductSpecification> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.getNullableResult(rs.getString(columnIndex));
    }

    @Override
    public List<ProductSpecification> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.getNullableResult(cs.getString(columnIndex));
    }

    private List<ProductSpecification> getNullableResult(String value) {
        return Objects.isNull(value) ? Collections.emptyList() : JsonUtils.parse(value, List.class, ProductSpecification.class);
    }
}
