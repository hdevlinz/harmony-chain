package com.tth.commonlibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "totalElements",
        "totalPages",
        "hasPrevious",
        "hasNext",
        "isFirst",
        "isLast",
        "page",
        "size",
        "data",
})
public class PageResponse<T> {

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    @JsonProperty("isFirst")
    private boolean isFirst;

    @JsonProperty("isLast")
    private boolean isLast;

    private boolean hasPrevious;

    private boolean hasNext;

    private List<T> data;

    public static <T> PageResponse<T> of(Page<T> page) {
        PageResponse<T> response = new PageResponse<>();
        response.setPage(page.getContent().isEmpty() ? 1 : page.getNumber() + 1);
        response.setSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(!page.getContent().isEmpty() && page.isLast());
        response.setFirst(!page.getContent().isEmpty() && page.isFirst());
        response.setHasPrevious(!page.getContent().isEmpty() && page.hasPrevious());
        response.setHasNext(!page.getContent().isEmpty() && page.hasNext());
        response.setData(page.getContent());

        return response;
    }

}
