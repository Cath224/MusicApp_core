package ru.musicapp.coreservice.model.entity.user;


import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "user", schema = "core_service")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String email;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_role", schema = "core_service", inverseJoinColumns = {@JoinColumn(name = "role_id")}, joinColumns = {@JoinColumn(name = "user_id")})
    private Set<Role> roles = new HashSet<>();

    @Column(name = "credentials_id")
    private UUID credentialsIds;

    @OneToOne
    @JoinColumn(name = "credentials_id", insertable = false, updatable = false)
    private Credentials credentials;

    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<String> links;

    @CreationTimestamp(source = SourceType.DB)
    private OffsetDateTime createdTimestamp;

    @UpdateTimestamp(source = SourceType.DB)
    private OffsetDateTime updatedTimestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(email, user.email) && Objects.equals(credentialsIds, user.credentialsIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, credentialsIds);
    }
}
