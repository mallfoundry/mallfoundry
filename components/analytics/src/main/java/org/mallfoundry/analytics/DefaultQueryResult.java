package org.mallfoundry.analytics;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.analytics.schema.ObjectField;
import org.mallfoundry.data.PageList;

import java.util.List;

@Getter
@Setter
public class DefaultQueryResult extends PageList<List<Object>> implements QueryResult {

    private String reportName;

    private List<ObjectField> fields;

    public DefaultQueryResult(List<List<Object>> elements) {
        super(elements);
    }
}
