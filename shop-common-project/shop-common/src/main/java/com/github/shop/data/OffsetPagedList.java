package com.github.shop.data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@JsonPropertyOrder({"offset", "limit", "size", "total", "elements"})
public class OffsetPagedList<T> extends PagedList<T> {

    @Getter
    @Setter
    private long offset;

    @Getter
    @Setter
    private long limit;

    public static <T> OffsetPagedList<T> of(List<T> list, long offset, long limit, long total) {
        // unmodifiable list
        List<T> uList = Collections.unmodifiableList(list);
        OffsetPagedList<T> page = new OffsetPagedList<>();
        page.setTotal(total);
        page.setSize(uList.size());
        page.setElements(uList);
        page.setOffset(offset);
        page.setLimit(limit);
        return page;
    }
}
