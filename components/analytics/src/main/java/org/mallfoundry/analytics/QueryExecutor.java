package org.mallfoundry.analytics;

public interface QueryExecutor {

    QueryResult execute(ReportQuery query, Report report);
}
