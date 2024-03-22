package ru.musicapp.coreservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.musicapp.coreservice.model.dto.file.FileDto;
import ru.musicapp.coreservice.model.type.BucketName;

import java.io.InputStream;
import java.util.UUID;

public interface FileService extends GetEntityService<FileDto, UUID> {

    void upload(MultipartFile file, BucketName bucketName);

    InputStream downloadFile(UUID fileId, BucketName bucketName);
}
