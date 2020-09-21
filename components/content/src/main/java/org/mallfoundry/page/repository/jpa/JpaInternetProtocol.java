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

package org.mallfoundry.page.repository.jpa;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.page.InternetProtocol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "mf_page_view_ip")
public class JpaInternetProtocol implements InternetProtocol {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "page_id_")
    private String pageId;

    @Column(name = "customer_ip_")
    private String customerIp;

    @Column(name = "browsing_time_")
    private Date browsingTime;
}
