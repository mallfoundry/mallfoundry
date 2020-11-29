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

package org.mallfoundry.customer.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.customer.SearchTerm;
import org.mallfoundry.customer.SearchTermSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_customer_search_term")
@IdClass(JpaSearchTermId.class)
public class JpaSearchTerm extends SearchTermSupport {

    @Id
    @Column(name = "customer_id_")
    private String customerId;

    @Id
    @Column(name = "term_")
    private String term;

    @Column(name = "timestamp_")
    private long timestamp;

    @Column(name = "hits_")
    private int hits;

    public JpaSearchTerm(String customerId, String term) {
        this.customerId = customerId;
        this.term = term;
    }

    public static JpaSearchTerm of(SearchTerm term) {
        if (term instanceof JpaSearchTerm) {
            return (JpaSearchTerm) term;
        }
        var target = new JpaSearchTerm();
        BeanUtils.copyProperties(term, target);
        return target;
    }
}
