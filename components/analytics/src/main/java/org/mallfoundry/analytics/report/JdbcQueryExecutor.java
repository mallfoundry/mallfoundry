package org.mallfoundry.analytics.report;

import org.mallfoundry.analytics.schema.ObjectTypeManager;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class JdbcQueryExecutor implements QueryExecutor {

    private final ObjectTypeManager objectTypeManager;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final ListRowMapper listRowMapper = new ListRowMapper();

    public JdbcQueryExecutor(ObjectTypeManager objectTypeManager,
                             NamedParameterJdbcTemplate jdbcTemplate) {
        this.objectTypeManager = objectTypeManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public QueryResult execute(ReportQuery query, Report report) {
        List<List<Object>> list = this.jdbcTemplate.query(report.getStatement(), query.getParameters(), listRowMapper);
        var result = new DefaultQueryResult(list);
        if (Objects.nonNull(query.getResultType())) {
            var resultType = this.objectTypeManager.getObjectType(query.getResultType()).orElseThrow();
            result.setFields(resultType.getFields());
        }
        result.setReportName(report.getName());
        return result;
    }

    private static class ListRowMapper implements RowMapper<List<Object>> {

        @Override
        public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
            List<Object> row = new ArrayList<>();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                row.add(JdbcUtils.getResultSetValue(rs, i));
            }
            return row;
        }
    }
}
