package ru.musicapp.coreservice.service;

import java.io.InputStream;
import java.util.Set;

public interface MinioAdapterService {

    void upload(String filename, String bucketName, String mimeType, byte[] fileBytes);

    void uploadInputStream(String filename, String bucketName, String mimeType, InputStream is);

    void deleteByFilename(String filename, String bucketName);

    void deleteByFilenames(Set<String> filenames, String bucketName);

    boolean existsByName(String filename, String bucketName);

    InputStream downloadFile(String filename, String bucketName);
}
