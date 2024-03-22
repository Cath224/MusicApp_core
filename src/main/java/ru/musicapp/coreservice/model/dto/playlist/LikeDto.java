package ru.musicapp.coreservice.model.dto.playlist;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.musicapp.coreservice.model.dto.music.SongShortDto;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeDto {

    private UUID userId;

    private UUID songId;

    private SongShortDto song;

}
