package ru.musicapp.coreservice.model.entity.music;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.musicapp.coreservice.model.entity.user.User;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "album", schema = "core_service")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Column(name = "musician_id")
    private UUID musicianId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musician_id", insertable = false, updatable = false)
    private Musician musician;

    private Boolean ep;

    private Short year;

    private UUID fileId;

    private String shortDescription;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "album_genre", schema = "core_service", inverseJoinColumns = {@JoinColumn(name = "genre")}, joinColumns = {@JoinColumn(name = "album_id")})
    private Set<Genre> genres = new HashSet<>();

    @CreationTimestamp(source = SourceType.DB)
    private OffsetDateTime createdTimestamp;

    @UpdateTimestamp(source = SourceType.DB)
    private OffsetDateTime updatedTimestamp;

    @Column(name = "created_by")
    @CreatedBy
    private UUID createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    private UUID updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdByInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    private User updatedByInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album album)) return false;
        return Objects.equals(id, album.id) && Objects.equals(title, album.title) && Objects.equals(musicianId, album.musicianId) && Objects.equals(ep, album.ep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, musicianId, ep);
    }
}
