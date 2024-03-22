package ru.musicapp.coreservice.model;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetails {

    private UUID id;
    private String login;
    private Set<String> roles;

}
