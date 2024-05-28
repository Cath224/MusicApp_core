package ru.musicapp.coreservice.service.impl;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.musicapp.coreservice.service.MinioAdapterService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MinioAdapterServiceImpl implements MinioAdapterService {

    private static final int PART_SIZE = 5242880;

    private final MinioClient minioClient;


    @Override
    public void upload(String filename, String bucketName, String mimeType, byte[] fileBytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes)) {
            uploadInputStream(filename, bucketName, mimeType, bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void uploadInputStream(String filename, String bucketName, String mimeType, InputStream is) {
        try {
            byte[] bytes = is.readAllBytes();
            createOrGetBucket(bucketName);
            ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .contentType(mimeType)
                    .stream(new ByteArrayInputStream(bytes), bytes.length, Math.max(bytes.length, PART_SIZE))
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByFilename(String filename, String bucketName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByFilenames(Set<String> filenames, String bucketName) {
        minioClient.removeObjects(RemoveObjectsArgs.builder()
                .bucket(bucketName)
                .objects(filenames.stream().map(DeleteObject::new).collect(Collectors.toList()))
                .build());
    }

    @Override
    public boolean existsByName(String filename, String bucketName) {
        try {
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .build());
            return true;
        } catch (ErrorResponseException ex) {
            return false;
        } catch (InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream downloadFile(String filename, String bucketName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .build());
        } catch (InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException |
                 IOException | NoSuchAlgorithmException | ServerException | XmlParserException |
                 ErrorResponseException e) {
            throw new RuntimeException(e);
        }
    }


    private String createOrGetBucket(String bucketName) throws ServerException, InsufficientDataException,
            ErrorResponseException, IOException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean result = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (result) {
            return bucketName;
        }
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).objectLock(true).build());
        return bucketName;
    }
}
