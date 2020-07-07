package org.mallfoundry.analytics.report;

import org.mallfoundry.analytics.schema.ObjectField;
import org.mallfoundry.data.SliceList;

import java.util.List;

public interface QueryResult extends SliceList<List<Object>> {

    String getReportName();

    List<ObjectField> getFields();
}
