package ru.musicapp.coreservice.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.musicapp.coreservice.model.UserExtendedDetails;
import ru.musicapp.coreservice.model.dto.user.RoleDto;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Jwts.SIG.HS512;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    private static final TypeReference<Set<String>> ROLES_TYPE_REFERENCE = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper;

    @Value("${jwt.sign-key}")
    private String signKey;


    public String getSubject(String token) {
        Jws<Claims> claims = Jwts.parser()
                .decryptWith(Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8)))
                .verifyWith(Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token);
        return claims.getPayload().getSubject();
    }

    public String generateJwt(UserExtendedDetails userDetails) {
        Date currentDate = new Date();

        try {
            return Jwts.builder()
                    .claim("roles", objectMapper.writeValueAsString(userDetails.getRoles().stream().map(RoleDto::getCode).collect(Collectors.toSet())))
                    .subject(userDetails.getUsername())
                    .issuedAt(currentDate)
                    .expiration(DateUtils.addHours(currentDate, 12))
                    .signWith(Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8)), HS512)
                    .header()
                    .and()
                    .compact();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isTokenValid(String token, UserExtendedDetails userDetails) {
        try {
            Jws<Claims> claims = Jwts.parser().decryptWith(Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8)))
                    .decryptWith(Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8)))
                    .verifyWith(Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token);

            if (claims.getPayload().getExpiration() == null
                    || claims.getPayload().getExpiration().before(new Date())
                    || !claims.getPayload().getSubject().equals(userDetails.getUsername())
                    || claims.getPayload().get("roles", String.class) == null
            ) {
                return false;
            }
            Set<String> roles = objectMapper.readValue(claims.getPayload().get("roles", String.class), ROLES_TYPE_REFERENCE);
            return SetUtils.isEqualSet(userDetails.getRoles().stream().map(RoleDto::getCode).collect(Collectors.toSet()), roles);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
