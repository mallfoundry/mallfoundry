package org.mallfoundry.analytics.report;

import org.mallfoundry.data.Query;
import org.mallfoundry.data.QueryBuilder;

import java.util.Map;

public interface ReportQuery extends Query {

    String getResultType();

    void setResultType(String resultType);

    String getReportId();

    Map<String, Object> getParameters();

    void setParameters(Map<String, Object> parameters);

    void setParameter(String name, Object value);

    Builder toBuilder();

    interface Builder extends QueryBuilder<ReportQuery, Builder> {

        Builder resultType(String resultType);

        Builder parameters(Map<String, Object> parameters);

        Builder parameter(String name, Object value);
    }
}
