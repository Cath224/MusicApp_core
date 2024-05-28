package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.musicapp.coreservice.controller.api.GetController;
import ru.musicapp.coreservice.model.dto.file.FileDto;
import ru.musicapp.coreservice.model.dto.file.FileStreamDto;
import ru.musicapp.coreservice.model.type.BucketName;
import ru.musicapp.coreservice.model.type.PictureType;
import ru.musicapp.coreservice.service.FileService;
import ru.musicapp.coreservice.service.GetEntityService;

import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/public/files")
@RequiredArgsConstructor
public class FileController extends GetController<FileDto, UUID> {

    private final FileService service;

    @PostMapping(value = "picture/{type}/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadPicture(@RequestPart MultipartFile file, @PathVariable PictureType type, @PathVariable String id) {
        service.uploadPicture(file, type, id);
    }


    @PostMapping(value = "song/{songId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadSong(@RequestPart MultipartFile file, @PathVariable UUID songId) {
        service.uploadSong(file, songId);
    }

    @GetMapping("pictures/{id}/download")
    public ResponseEntity<StreamingResponseBody> downloadById(@PathVariable UUID id) {
        FileStreamDto dto = service.downloadFile(id, BucketName.PICTURE);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.builder("attachment").filename(dto.getFilename()).build().toString())
                .body((out) -> {
                    try (InputStream is = dto.getInputStream().get()) {
                        is.transferTo(out);
                    }
                });
    }

    @Override
    protected GetEntityService<FileDto, UUID> getEntityService() {
        return service;
    }
}
