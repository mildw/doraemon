package com.example.core.paging;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Getter
@NoArgsConstructor
public class PageRs {
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private long totalElements;

    public PageRs(Pageable pageable, int totalPages, long totalElements) {
        this.page = pageable.getPageNumber();
        this.size = pageable.getPageSize();
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public PageRs(int page, int size, int totalPages, int totalElements) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public PageRs(Pageable pageable, long totalElements) {
        this.page = pageable.getPageNumber();
        this.size = pageable.getPageSize();
        this.totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
        this.totalElements = totalElements;
    }

    public PageRs(Page<?> page) {
        this.page = page.getPageable().getPageNumber();
        this.size = page.getPageable().getPageSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}
