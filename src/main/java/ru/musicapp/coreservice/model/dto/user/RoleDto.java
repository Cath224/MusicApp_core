package ru.musicapp.coreservice.model.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto {

    private UUID id;

    private String code;

    private Set<AuthorityDto> authorities;

    private String title;

}
