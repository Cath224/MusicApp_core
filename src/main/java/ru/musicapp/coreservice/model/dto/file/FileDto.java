package ru.musicapp.coreservice.model.dto.file;

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
public class FileDto {

    private UUID id;

    private String filename;

    private String mimeType;

    private OffsetDateTime createdTimestamp;
    private OffsetDateTime updatedTimestamp;
    private UUID createdBy;
    private UUID updatedBy;

}
