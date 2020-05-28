/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mallfoundry.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@IdClass(InternalSearchTermId.class)
public class InternalSearchTerm implements SearchTerm {

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

    public InternalSearchTerm(String customerId, String term) {
        this.customerId = customerId;
        this.term = term;
        this.incrementHits();
    }

    @Override
    public int incrementHits() {
        this.timestamp = System.currentTimeMillis();
        return hits++;
    }

    @Override
    public int decrementHits() {
        this.timestamp = System.currentTimeMillis();
        return hits--;
    }
}
