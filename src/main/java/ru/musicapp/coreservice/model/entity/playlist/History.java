package ru.musicapp.coreservice.model.entity.playlist;

import jakarta.persistence.*;
import lombok.*;
import ru.musicapp.coreservice.model.entity.music.Song;
import ru.musicapp.coreservice.model.entity.user.User;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "history", schema = "core_service")
@Entity
public class History {

    @EmbeddedId
    private HistoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", insertable = false, updatable = false)
    private Song song;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class HistoryId implements Serializable {

        @Column(name = "user_id")
        private UUID userId;

        @Column(name = "song_id")
        private UUID songId;

    }

}
