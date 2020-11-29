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

package org.mallfoundry.data;


import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class PageableSupport implements Pageable {
    private Integer page = DEFAULT_PAGE;

    private Integer limit = DEFAULT_LIMIT;

    public void setPage(Integer page) {
        if (Objects.nonNull(page)) {
            this.page = Math.max(DEFAULT_PAGE, page);
        }
    }

    public void setLimit(Integer limit) {
        if (Objects.nonNull(limit)) {
            this.limit = Math.min(Math.max(MIN_LIMIT, limit), MAX_LIMIT);
        }
    }
}
