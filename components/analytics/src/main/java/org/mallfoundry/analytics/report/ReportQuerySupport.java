package org.mallfoundry.analytics.report;

import org.apache.commons.collections4.MapUtils;
import org.mallfoundry.analytics.report.ReportQuery;
import org.mallfoundry.data.QueryBuilderSupport;
import org.mallfoundry.data.QuerySupport;

import java.util.Map;

public abstract class ReportQuerySupport extends QuerySupport implements ReportQuery {

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport
            extends QueryBuilderSupport<ReportQuery, Builder> implements Builder {

        protected final ReportQuery query;

        protected BuilderSupport(ReportQuery query) {
            super(query);
            this.query = query;
        }

        @Override
        public Builder resultType(String resultType) {
            this.query.setResultType(resultType);
            return this;
        }

        @Override
        public Builder parameters(Map<String, Object> parameters) {
            if (MapUtils.isNotEmpty(parameters)) {
                parameters.forEach(this::parameter);
            }
            return this;
        }

        @Override
        public Builder parameter(String name, Object value) {
            this.query.setParameter(name, value);
            return this;
        }
    }
}
