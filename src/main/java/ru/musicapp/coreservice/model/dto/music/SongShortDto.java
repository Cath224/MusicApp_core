package ru.musicapp.coreservice.model.dto.music;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SongShortDto {

    private UUID id;

    private String title;

    private UUID albumId;

    private Double duration;

    private UUID fileId;

}
