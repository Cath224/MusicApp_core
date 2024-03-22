package ru.musicapp.coreservice.model.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private UUID id;

    private String login;

    private String name;

    private String email;

    private List<String> links;

    private List<RoleDto> roles;


    private OffsetDateTime createdTimestamp;


    private OffsetDateTime updatedTimestamp;


}
