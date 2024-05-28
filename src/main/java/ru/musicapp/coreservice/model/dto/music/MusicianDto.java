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
public class MusicianDto {

    private UUID id;

    private String title;

    private UUID fileId;

    private String description;

    private String shortDescription;

    private String[] links;

    private String countryCode;

    private OffsetDateTime createdTimestamp;

    private OffsetDateTime updatedTimestamp;

    private UUID createdBy;

    private UUID updatedBy;

}
