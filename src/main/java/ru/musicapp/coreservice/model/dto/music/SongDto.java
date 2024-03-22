package ru.musicapp.coreservice.model.dto.music;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SongDto {

    private UUID id;

    private String title;

    private UUID albumId;

    private Integer duration;

    private UUID fileId;

    private String lyrics;


    private OffsetDateTime createdTimestamp;


    private OffsetDateTime updatedTimestamp;

    private UUID createdBy;

    private UUID updatedBy;

}
