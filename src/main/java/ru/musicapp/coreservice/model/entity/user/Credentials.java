package ru.musicapp.coreservice.model.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "credentials", schema = "core_service")
@Entity
public class Credentials {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String login;

    private String password;

    @Column(name = "salt_id")
    private UUID saltId;

    @OneToOne
    @JoinColumn(name = "salt_id", insertable = false, updatable = false)
    private Salt salt;

    @CreationTimestamp(source = SourceType.DB)
    private OffsetDateTime createdTimestamp;

    @UpdateTimestamp(source = SourceType.DB)
    private OffsetDateTime updatedTimestamp;


    @PostPersist
    private void postPersist() {
        if (this.createdTimestamp == null) {
            this.createdTimestamp = OffsetDateTime.now();
        }
        this.updatedTimestamp = OffsetDateTime.now();
    }

    @PostUpdate
    private void postUpdate() {
        if (this.createdTimestamp == null) {
            this.createdTimestamp = OffsetDateTime.now();
        }
        this.updatedTimestamp = OffsetDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credentials that)) return false;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
