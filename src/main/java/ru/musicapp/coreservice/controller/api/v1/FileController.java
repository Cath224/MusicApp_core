package ru.musicapp.coreservice.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.musicapp.coreservice.controller.api.GetController;
import ru.musicapp.coreservice.model.dto.file.FileDto;
import ru.musicapp.coreservice.model.type.BucketName;
import ru.musicapp.coreservice.service.FileService;
import ru.musicapp.coreservice.service.GetEntityService;

import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/public/files")
@RequiredArgsConstructor
public class FileController extends GetController<FileDto, UUID> {

    private final FileService service;

    @PostMapping("pictures")
    public void uploadPicture(@RequestPart MultipartFile file) {
        service.upload(file, BucketName.PICTURE);
    }


    @PostMapping("songs")
    public void uploadSong(@RequestPart MultipartFile file) {
        service.upload(file, BucketName.SONG);
    }

    @GetMapping("pictures/{id}/download")
    public ResponseEntity<StreamingResponseBody> downloadById(@PathVariable UUID id) {
        return ResponseEntity.ok()
                .body((out) -> {
                    try (InputStream is = service.downloadFile(id, BucketName.PICTURE)) {
                        is.transferTo(out);
                    }
                });
    }

    @Override
    protected GetEntityService<FileDto, UUID> getEntityService() {
        return service;
    }
}
