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

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface Pageable {

    int DEFAULT_PAGE = 1;

    int DEFAULT_LIMIT = 20;

    int MIN_LIMIT = 1;

    int MAX_LIMIT = 100;

    Integer getPage();

    void setPage(Integer page);

    Integer getLimit();

    void setLimit(Integer limit);
}