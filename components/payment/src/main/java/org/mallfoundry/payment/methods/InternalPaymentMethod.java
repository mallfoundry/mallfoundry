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

package org.mallfoundry.payment.methods;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_payment_method")
public class InternalPaymentMethod implements PaymentMethod {

    @Id
    @Column(name = "code_")
    private String code;

    @Column(name = "name_")
    private String name;

    @Column(name = "color_")
    private String color;

    @Column(name = "logo_")
    private String logo;

    @Column(name = "description_")
    private String description;

    @Column(name = "enabled_")
    private boolean enabled;

    @Column(name = "position_")
    private int position;

    public InternalPaymentMethod(String code) {
        this.code = code;
    }

    public static InternalPaymentMethod of(PaymentMethod method) {
        if (method instanceof InternalPaymentMethod) {
            return (InternalPaymentMethod) method;
        }
        var target = new InternalPaymentMethod();
        BeanUtils.copyProperties(method, target);
        return target;
    }
}
