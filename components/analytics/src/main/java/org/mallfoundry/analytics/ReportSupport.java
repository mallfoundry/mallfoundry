package org.mallfoundry.analytics;

public abstract class ReportSupport implements Report {

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final ReportSupport report;

        BuilderSupport(ReportSupport report) {
            this.report = report;
        }

        @Override
        public Builder name(String name) {
            this.report.setName(name);
            return this;
        }

        @Override
        public Builder statement(String statement) {
            this.report.setStatement(statement);
            return this;
        }

        @Override
        public Builder statementType(ReportStatementType statementType) {
            this.report.setStatementType(statementType);
            return this;
        }

        @Override
        public Report build() {
            return this.report;
        }
    }
}
