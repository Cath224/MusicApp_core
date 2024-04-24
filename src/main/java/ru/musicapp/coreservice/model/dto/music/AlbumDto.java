package ru.musicapp.coreservice.model.dto.music;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumDto {

    private UUID id;

    private String title;

    private UUID musicianId;

    private Boolean ep;

    private Short year;

    private UUID fileId;

    private String shortDescription;

    private Set<String> genres;

    private OffsetDateTime createdTimestamp;

    private OffsetDateTime updatedTimestamp;

    private UUID createdBy;

    private UUID updatedBy;

}
