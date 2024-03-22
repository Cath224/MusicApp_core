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

@Table(name = "salt", schema = "core_service")
@Entity
public class Salt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String value;

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
        if (!(o instanceof Salt salt)) return false;
        return Objects.equals(id, salt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
