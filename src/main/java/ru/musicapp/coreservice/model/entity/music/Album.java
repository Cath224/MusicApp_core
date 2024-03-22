package ru.musicapp.coreservice.model.entity.music;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import ru.musicapp.coreservice.model.entity.user.User;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "album", schema = "core_service")
@Entity
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

    @CreationTimestamp(source = SourceType.DB)
    private OffsetDateTime createdTimestamp;

    @UpdateTimestamp(source = SourceType.DB)
    private OffsetDateTime updatedTimestamp;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private User createdByInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    private User updatedByInfo;
}
