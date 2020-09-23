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

package org.mallfoundry.edw.page.jpa;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.edw.page.DailyPageFact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "mf_edw_daily_page_fact")
@IdClass(JpaDailyPageFactId.class)
public class JpaDailyPageFact implements DailyPageFact {

    @Id
    @Column(name = "tenant_key_")
    private String tenantKey;

    @Id
    @Column(name = "store_key_")
    private String storeKey;

    @Id
    @Column(name = "page_key_")
    private String pageKey;

    @Id
    @Column(name = "page_type_key_")
    private String pageTypeKey;

    @Id
    @Column(name = "browser_key_")
    private String browserKey;

    @Id
    @Column(name = "browser_ip_key_")
    private String browserIpKey;

    @Id
    @Column(name = "date_key_")
    private int dateKey;

    @Column(name = "view_count_")
    private int viewCount;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaDailyPageFact)) {
            return false;
        }
        JpaDailyPageFact that = (JpaDailyPageFact) object;
        return dateKey == that.dateKey
                && Objects.equals(tenantKey, that.tenantKey)
                && Objects.equals(storeKey, that.storeKey)
                && Objects.equals(pageKey, that.pageKey)
                && Objects.equals(pageTypeKey, that.pageTypeKey)
                && Objects.equals(browserKey, that.browserKey)
                && Objects.equals(browserIpKey, that.browserIpKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantKey, storeKey, pageKey, pageTypeKey, browserKey, browserIpKey, dateKey);
    }
}
