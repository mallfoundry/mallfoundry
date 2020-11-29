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

package org.mallfoundry.store.staff.repository.jpa;


import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.store.staff.StaffStatus;
import org.mallfoundry.store.staff.StaffSupport;
import org.mallfoundry.store.staff.StaffType;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class JpaStaffBase extends StaffSupport {

    @Id
    @NotBlank
    @Column(name = "id_")
    private String id;

    @Id
    @NotBlank
    @Column(name = "store_id_")
    private String storeId;

    @NotBlank
    @Column(name = "tenant_id_")
    private String tenantId;

    @NotNull
    @Column(name = "type_")
    private StaffType type;

    @NotNull
    @Column(name = "status_")
    private StaffStatus status;

    @NotBlank
    @Column(name = "name_")
    private String name;

    @Column(name = "number_")
    private String number;

    @NotBlank
    @Column(name = "avatar_")
    private String avatar;

    @NotBlank
    @Column(name = "country_code_")
    private String countryCode;

    @NotBlank
    @Column(name = "phone_")
    private String phone;

    @NotNull
    @Column(name = "created_time_")
    private Date createdTime;
}
