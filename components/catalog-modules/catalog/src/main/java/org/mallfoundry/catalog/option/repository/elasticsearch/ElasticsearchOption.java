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

package org.mallfoundry.catalog.option.repository.elasticsearch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.option.Option;
import org.mallfoundry.catalog.option.OptionSupport;
import org.mallfoundry.catalog.option.OptionValue;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ElasticsearchOption extends OptionSupport {

    private String id;

    private String name;

    private List<OptionValue> values = new ArrayList<>();

    private int position;

    public ElasticsearchOption(String id) {
        super(id);
    }

    public static ElasticsearchOption of(Option option) {
        if (option instanceof ElasticsearchOption) {
            return (ElasticsearchOption) option;
        }
        var target = new ElasticsearchOption();
        BeanUtils.copyProperties(option, target);
        return target;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ElasticsearchOption)) {
            return false;
        }
        ElasticsearchOption that = (ElasticsearchOption) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
