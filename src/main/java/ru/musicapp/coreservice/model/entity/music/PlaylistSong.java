package ru.musicapp.coreservice.model.entity.music;

import jakarta.persistence.*;
import lombok.*;
import ru.musicapp.coreservice.model.entity.playlist.Playlist;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "playlist_song", schema = "core_service")
@Entity
public class PlaylistSong {

    @EmbeddedId
    private PlaylistSongId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", insertable = false, updatable = false)
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", insertable = false, updatable = false)
    private Song song;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class PlaylistSongId implements Serializable {

        @Column(name = "playlist_id")
        private UUID playlistId;

        @Column(name = "song_id")
        private UUID songId;
    }

}
