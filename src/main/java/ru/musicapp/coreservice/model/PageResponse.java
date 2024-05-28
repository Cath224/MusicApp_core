package ru.musicapp.coreservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse<E> {

    private List<E> content;
    private PageResponseMeta meta;

    public static <E> PageResponse<E> of(Page<E> page) {
        return PageResponse.<E>builder()
                .content(page.getContent())
                .meta(PageResponseMeta.builder()
                        .offset(page.getPageable().getPageNumber())
                        .limit(page.getPageable().getPageSize())
                        .totalElements(page.getTotalElements())
                        .build())
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static final class PageResponseMeta {
        private int offset;
        private int limit;
        private long totalElements;
    }

}
