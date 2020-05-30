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
