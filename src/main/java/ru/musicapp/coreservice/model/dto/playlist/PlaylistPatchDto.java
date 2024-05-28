package ru.musicapp.coreservice.model.dto.playlist;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistPatchDto {

    private String title;

    private UUID fileId;

    private Boolean privatePlaylist;

    private Set<UUID> songIds;
}
