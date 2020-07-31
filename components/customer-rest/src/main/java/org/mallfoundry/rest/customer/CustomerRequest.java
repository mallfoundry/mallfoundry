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

package org.mallfoundry.rest.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.customer.Customer;
import org.mallfoundry.identity.Gender;

import java.util.Date;

@Getter
@Setter
@Schema
public class CustomerRequest {

    @Schema(name = "avatar")
    private String avatar;

    @Schema(name = "nickname")
    private String nickname;

    @Schema(name = "gender")
    private Gender gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(name = "birthdate_")
    private Date birthdate;

    public Customer assignToCustomer(Customer customer) {
        return customer.toBuilder()
                .avatar(this.avatar)
                .nickname(this.nickname)
                .gender(this.gender)
                .birthdate(this.birthdate)
                .build();
    }
}
