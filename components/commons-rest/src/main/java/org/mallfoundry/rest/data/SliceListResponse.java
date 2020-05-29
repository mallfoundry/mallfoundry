package org.mallfoundry.rest.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

//@Schema
@Getter
public class SliceListResponse {

    private int page;

    private int limit;

    private int size;

    @Schema(name = "total_pages")
    private int totalPages;

    @Schema(name = "total_size")
    private int totalSize;

//    int getPage();
//
//    int getLimit();
//
//    List<T> getElements();
//
//    int getSize();
//
//    int getTotalPages();
//
//    long getTotalSize();

}
