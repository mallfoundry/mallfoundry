/*
 * Copyright (C) 2019-2021 the original author or authors.
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

package org.mallfoundry.catalog.option.repository.jpa;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.option.DefaultOptionValue;
import org.mallfoundry.catalog.option.OptionSupport;
import org.mallfoundry.catalog.option.OptionValue;
import org.mallfoundry.catalog.product.repository.jpa.convert.ProductOptionValueListConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_catalog_product_option")
public class JpaOption extends OptionSupport {

    @NotBlank
    @Id
    @Column(name = "id_")
    private String id;

    @NotBlank
    @Column(name = "name_")
    private String name;

    @Valid
    @Column(name = "values_", length = 1024)
    @Convert(converter = ProductOptionValueListConverter.class)
    @JsonDeserialize(contentAs = DefaultOptionValue.class)
    private List<OptionValue> values = new ArrayList<>();

    @Column(name = "position_")
    private int position;

    public JpaOption(String id) {
        super(id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaOption)) {
            return false;
        }
        JpaOption that = (JpaOption) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
