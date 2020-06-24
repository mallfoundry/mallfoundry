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

package org.mallfoundry.rest.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.customer.Customer;
import org.mallfoundry.customer.Gender;

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
