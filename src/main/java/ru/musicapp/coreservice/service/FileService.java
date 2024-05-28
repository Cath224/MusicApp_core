package ru.musicapp.coreservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.musicapp.coreservice.model.dto.file.FileDto;
import ru.musicapp.coreservice.model.dto.file.FileStreamDto;
import ru.musicapp.coreservice.model.type.BucketName;
import ru.musicapp.coreservice.model.type.PictureType;

import java.util.UUID;

public interface FileService extends GetEntityService<FileDto, UUID> {

    void upload(MultipartFile file, BucketName bucketName);

    void uploadPicture(MultipartFile file, PictureType type, String id);

    void uploadSong(MultipartFile file, UUID songId);

    FileStreamDto downloadFile(UUID fileId, BucketName bucketName);
}
