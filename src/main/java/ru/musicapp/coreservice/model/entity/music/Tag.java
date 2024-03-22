package ru.musicapp.coreservice.model.entity.music;

import jakarta.persistence.*;
import lombok.*;
import ru.musicapp.coreservice.model.entity.user.User;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tag", schema = "core_service")
@Entity
public class Tag {

    @Id
    private String title;

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
