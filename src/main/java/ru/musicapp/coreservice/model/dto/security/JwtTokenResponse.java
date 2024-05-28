package ru.musicapp.coreservice.model.dto.security;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokenResponse {

    private String token;

}
