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

package org.mallfoundry.rest.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.mallfoundry.data.SliceList;

import java.util.List;

//@Schema
@Getter
public class SliceListResponse<T> {

    @Schema(title = "当前页数")
    private int page;

    @Schema(title = "限制当前页数的数量")
    private int limit;

    @Schema(title = "当前页数的数量")
    private int size;

    @Schema(title = "总页数")
    private int totalPages;

    @Schema(title = "总数量")
    private long totalSize;

    @Schema(title = "元素集合")
    private List<T> elements;

    public static <E> SliceListResponse<E> of(SliceList<E> list) {
        var response = new SliceListResponse<E>();
        response.page = list.getPage();
        response.limit = list.getLimit();
        response.size = list.getSize();
        response.totalPages = list.getTotalPages();
        response.totalSize = list.getTotalSize();
        response.elements = list.getElements();
        return response;
    }
}
