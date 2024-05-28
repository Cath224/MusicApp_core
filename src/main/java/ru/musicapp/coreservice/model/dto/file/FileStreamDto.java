package ru.musicapp.coreservice.model.dto.file;

import lombok.*;

import java.io.InputStream;
import java.util.function.Supplier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileStreamDto {

    private String filename;
    private Supplier<InputStream> inputStream;

}
