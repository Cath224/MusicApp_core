package ru.musicapp.coreservice.model.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreateDto {

    private String login;

    private String password;

    private String name;

    private String email;

    private List<String> links;

}
