package com.mallfoundry.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PagedList<T> implements Iterable<T> {

    @Getter
    @Setter
    private long total;

    @Getter
    @Setter
    private long size;

    @Setter
    private List<T> elements;

    public List<T> getElements() {
        return this.elements == null ? Collections.emptyList() : this.elements;
    }

    @Override
    public Iterator<T> iterator() {
        return this.getElements().iterator();
    }

    public static <T> PagedList<T> of(List<T> list, long total) {
        // unmodifiable list
        List<T> uList = Collections.unmodifiableList(list);
        PagedList<T> page = new PagedList<>();
        page.setTotal(total);
        page.setSize(uList.size());
        page.setElements(uList);
        return page;
    }

    public static <T> PagedList<T> empty() {
        PagedList<T> page = new PagedList<>();
        page.setTotal(0);
        page.setSize(0);
        page.setElements(Collections.emptyList());
        return page;
    }


}
