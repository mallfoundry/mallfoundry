/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.analytics.report.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.analytics.report.Report;
import org.mallfoundry.analytics.report.ReportSupport;
import org.mallfoundry.analytics.report.ReportStatementType;
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
@Table(name = "mf_analytics_report")
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
