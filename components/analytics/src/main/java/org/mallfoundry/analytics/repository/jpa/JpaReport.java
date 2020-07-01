package org.mallfoundry.analytics.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.analytics.Report;
import org.mallfoundry.analytics.ReportSupport;
import org.mallfoundry.analytics.ReportStatementType;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_report")
public class JpaReport extends ReportSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Column(name = "statement_")
    private String statement;

    @Enumerated(EnumType.STRING)
    @Column(name = "statement_type_")
    private ReportStatementType statementType;

    public JpaReport(String id) {
        this.id = id;
    }

    public static JpaReport of(Report query) {
        if (query instanceof JpaReport) {
            return (JpaReport) query;
        }
        var target = new JpaReport();
        BeanUtils.copyProperties(query, target);
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JpaReport)) {
            return false;
        }
        JpaReport jpaReport = (JpaReport) o;
        return Objects.equals(id, jpaReport.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
