package com.rcet.backend.util;

import com.rcet.backend.dto.response.PageResponse;
import org.springframework.data.domain.Page;

public class PageUtil {

    private PageUtil() {
    }

    public static <T> PageResponse<T> from(Page<T> page) {

        return PageResponse.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .build();
    }
}