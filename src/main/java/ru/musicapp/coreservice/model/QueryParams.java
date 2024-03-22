package ru.musicapp.coreservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryParams {

    private String query;
    private Integer offset = 0;
    private Integer limit = 1000;
    private Set<String> sortColumns;
    private String direction = Sort.Direction.ASC.name();


    public Pageable getPageRequest() {
        if (CollectionUtils.isNotEmpty(sortColumns)) {
            return PageRequest.of(getOffset() / getLimit(), getLimit(), getSort());
        }
        return PageRequest.of(getOffset() / getLimit(), getLimit());
    }

    private Sort getSort() {
        return Sort.by(Sort.Direction.fromString(getDirection()), sortColumns.toArray(String[]::new));
    }

}
