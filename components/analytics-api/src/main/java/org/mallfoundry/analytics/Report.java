package org.mallfoundry.analytics;

import org.mallfoundry.util.ObjectBuilder;

public interface Report {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getStatement();

    void setStatement(String statement);

    ReportStatementType getStatementType();

    void setStatementType(ReportStatementType statementType);

    Builder toBuilder();

    interface Builder extends ObjectBuilder<Report> {

        Builder name(String name);

        Builder statement(String statement);

        Builder statementType(ReportStatementType statementType);
    }
}
