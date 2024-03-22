package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.musicapp.coreservice.mapper.EntityMapper;
import ru.musicapp.coreservice.mapper.FileMapper;
import ru.musicapp.coreservice.model.dto.file.FileDto;
import ru.musicapp.coreservice.model.entity.file.File;
import ru.musicapp.coreservice.model.type.BucketName;
import ru.musicapp.coreservice.repository.EntityRepository;
import ru.musicapp.coreservice.repository.FileRepository;
import ru.musicapp.coreservice.service.FileService;
import ru.musicapp.coreservice.service.MinioAdapterService;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl extends GetEntityServiceImpl<File, FileDto, UUID> implements FileService {

    private final FileRepository repository;
    private final FileMapper mapper;
    private final MinioAdapterService minioAdapterService;

    @Transactional
    @Override
    public void upload(MultipartFile multipartFile, BucketName bucketName) {
        String mediaType = multipartFile.getContentType();
        if (BucketName.PICTURE == bucketName) {
            if (!MediaType.IMAGE_JPEG_VALUE.equals(mediaType)) {
                throw new RuntimeException();
            }
        } else if (BucketName.SONG == bucketName) {
            if (!"audio/wave".equals(mediaType)) {
                throw new RuntimeException();
            }
        }
        try (InputStream is = multipartFile.getInputStream()) {
            String filename = UUID.randomUUID() + "." + FileNameUtils.getExtension(multipartFile.getOriginalFilename());
            while (minioAdapterService.existsByName(filename, bucketName.getTitle())) {
                filename = UUID.randomUUID().toString();
            }
            minioAdapterService.uploadInputStream(filename, bucketName.getTitle(), multipartFile.getContentType(), is);
            File file = File.builder()
                    .filename(filename)
                    .mimeType(multipartFile.getContentType())
                    .build();
            repository.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public InputStream downloadFile(UUID id, BucketName bucketName) {
        FileDto dto = getById(id);
        return minioAdapterService.downloadFile(dto.getFilename(), bucketName.getTitle());
    }

    @Override
    protected EntityRepository<File, UUID> getRepository() {
        return repository;
    }

    @Override
    protected EntityMapper<File, FileDto> getMapper() {
        return mapper;
    }
}
