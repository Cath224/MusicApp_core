package ru.musicapp.coreservice.model.dto.error;

import lombok.*;
import org.springframework.context.annotation.Bean;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ErrorDto {

    private String message;

}
