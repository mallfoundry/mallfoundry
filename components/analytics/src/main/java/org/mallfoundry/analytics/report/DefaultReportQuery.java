package org.mallfoundry.analytics.report;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class DefaultReportQuery extends ReportQuerySupport {

    private String resultType;

    private String reportId;

    private Map<String, Object> parameters = new HashMap<>();

    public DefaultReportQuery(String reportId) {
        this.reportId = reportId;
    }

    @Override
    public void setParameter(String name, Object value) {
        this.parameters.put(name, value);
    }
}
