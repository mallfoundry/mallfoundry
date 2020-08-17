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
import org.mallfoundry.customer.Customer;
import org.mallfoundry.customer.CustomerAddress;
import org.mallfoundry.customer.CustomerId;
import org.mallfoundry.customer.CustomerSupport;
import org.mallfoundry.identity.Gender;
import org.mallfoundry.identity.User;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_customer")
public class JpaCustomer extends CustomerSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "tenant_id_")
    private String tenantId;

    @Column(name = "username_")
    private String username;

    @Column(name = "avatar_")
    private String avatar;

    @Column(name = "nickname_")
    private String nickname;

    @Column(name = "gender_")
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate_")
    private Date birthdate;

    @OneToMany(targetEntity = JpaCustomerAddress.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id_")
    @OrderBy("defaulted DESC , createdTime ASC")
    private List<CustomerAddress> addresses = new ArrayList<>();

    public JpaCustomer(User user) {
        this.id = user.getId();
        this.tenantId = user.getTenantId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.nickname = user.getNickname();
        this.gender = user.getGender();
        this.birthdate = new Date();
    }

    public JpaCustomer(CustomerId customerId) {
        this.tenantId = customerId.getTenantId();
        this.id = customerId.getId();
    }

    public static JpaCustomer of(Customer customer) {
        if (customer instanceof JpaCustomer) {
            return (JpaCustomer) customer;
        }
        var target = new JpaCustomer();
        BeanUtils.copyProperties(customer, target);
        return target;
    }

    @Override
    public CustomerAddress createAddress(String id) {
        return new JpaCustomerAddress(id);
    }
}
