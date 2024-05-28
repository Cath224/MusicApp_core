package ru.musicapp.coreservice.model.dto.playlist;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistQueueElement {

    private LinkedHashSet<UUID> songIds;

}
