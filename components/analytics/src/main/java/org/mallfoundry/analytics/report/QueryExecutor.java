package org.mallfoundry.analytics.report;

import org.mallfoundry.analytics.report.QueryResult;
import org.mallfoundry.analytics.report.Report;
import org.mallfoundry.analytics.report.ReportQuery;

public interface QueryExecutor {

    QueryResult execute(ReportQuery query, Report report);
}
