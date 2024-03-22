package ru.musicapp.coreservice.model.dto.music;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumPatchDto {

    private String title;

    private Boolean ep;

    private Short year;

    private String shortDescription;

}
