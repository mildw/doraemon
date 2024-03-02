package com.example.core.paging;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageRq {

    private Integer page;

    private Integer size;

    private Sort.Direction direction;

    private String property;

    public Pageable toPageable() {
        if (direction != null && property != null) {
            Sort sort = Sort.by(new Sort.Order(this.direction, this.property));
            return PageRequest.of(this.page, this.size, sort);
        }

        return PageRequest.of(this.page, this.size);
    }

    public PageRq(Integer page, Integer size, Sort.Direction direction, String property) {
        this.page = page;
        this.size = size;
        this.direction = direction;
        this.property = property;
    }

}
