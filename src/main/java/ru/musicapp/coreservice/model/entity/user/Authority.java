package ru.musicapp.coreservice.model.entity.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "authority", schema = "core_service")
@Entity
public class Authority {

    @Id
    private String code;

    private String title;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_authority", schema = "core_service", joinColumns = {@JoinColumn(name = "authority_code")})
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority authority)) return false;
        return Objects.equals(code, authority.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
