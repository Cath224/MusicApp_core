package ru.musicapp.coreservice.model;

import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.musicapp.coreservice.model.dto.user.RoleDto;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserExtendedDetails implements UserDetails {

    private UUID id;
    private String login;
    private String password;
    private Set<RoleDto> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return CollectionUtils.isEmpty(roles) ? Collections.emptyList() : roles.stream()
                .map(RoleDto::getAuthorities)
                .filter(CollectionUtils::isNotEmpty)
                .flatMap(Collection::stream)
                .map((el) -> new SimpleGrantedAuthority(el.getCode()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
