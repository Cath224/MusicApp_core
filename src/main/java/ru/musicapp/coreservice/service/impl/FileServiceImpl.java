package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.musicapp.coreservice.mapper.EntityMapper;
import ru.musicapp.coreservice.mapper.FileMapper;
import ru.musicapp.coreservice.model.dto.file.FileDto;
import ru.musicapp.coreservice.model.dto.file.FileStreamDto;
import ru.musicapp.coreservice.model.entity.file.File;
import ru.musicapp.coreservice.model.entity.music.Album;
import ru.musicapp.coreservice.model.entity.music.Musician;
import ru.musicapp.coreservice.model.entity.music.Song;
import ru.musicapp.coreservice.model.entity.playlist.Playlist;
import ru.musicapp.coreservice.model.type.BucketName;
import ru.musicapp.coreservice.model.type.PictureType;
import ru.musicapp.coreservice.repository.*;
import ru.musicapp.coreservice.service.FileService;
import ru.musicapp.coreservice.service.MinioAdapterService;
import ru.musicapp.coreservice.service.MusicianService;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.spi.AudioFileReader;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl extends GetEntityServiceImpl<File, FileDto, UUID> implements FileService {

    private final FileRepository repository;
    private final FileMapper mapper;
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final MusicianRepository musicianRepository;
    private final PlaylistRepository playlistRepository;
    private final MinioAdapterService minioAdapterService;

    @Transactional
    @Override
    public void upload(MultipartFile multipartFile, BucketName bucketName) {
        try (InputStream is = multipartFile.getInputStream()) {
            String filename = UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
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

    @Transactional
    @Override
    public void uploadPicture(MultipartFile multipartFile, PictureType type, String id) {
        String contentType = multipartFile.getContentType();
        if (!Objects.equals(MediaType.IMAGE_JPEG_VALUE, contentType)) {
            throw new RuntimeException();
        }
        String filename = UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        File file = File.builder()
                .filename(filename)
                .mimeType(multipartFile.getContentType())
                .build();
        file = repository.save(file);
        switch (type) {
            case ALBUM -> {
                Album album = albumRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException());
                album.setFileId(file.getId());
                albumRepository.save(album);
                break;
            }
            case PLAYLIST -> {
                Playlist playlist = playlistRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException());
                playlist.setFileId(file.getId());
                playlistRepository.save(playlist);
                break;
            }
            case MUSICIAN -> {
                Musician musician = musicianRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException());
                musician.setFileId(file.getId());
                musicianRepository.save(musician);
                break;
            }
            default -> {
                break;
            }
        }
        try (InputStream is = multipartFile.getInputStream()) {
            while (minioAdapterService.existsByName(filename, BucketName.PICTURE.getTitle())) {
                filename = UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            }
            minioAdapterService.uploadInputStream(filename, BucketName.PICTURE.getTitle(), multipartFile.getContentType(), is);
            file = repository.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void uploadSong(MultipartFile multipartFile, UUID songId) {
        String contentType = multipartFile.getContentType();
        if (!Objects.equals("audio/wave", contentType)) {
            throw new RuntimeException();
        }
        Song song = songRepository.findById(songId).orElseThrow(() -> new RuntimeException());
        BucketName bucketName = BucketName.SONG;
        try (BufferedInputStream bis = new BufferedInputStream(multipartFile.getInputStream());
             AudioInputStream ais = AudioSystem.getAudioInputStream(bis)
        ) {
            String filename = UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            while (minioAdapterService.existsByName(filename, bucketName.getTitle())) {
                filename = UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            }
            minioAdapterService.upload(filename, bucketName.getTitle(), multipartFile.getContentType(), multipartFile.getBytes());
            File file = File.builder()
                    .filename(filename)
                    .mimeType(multipartFile.getContentType())
                    .build();
            file = repository.save(file);
            song.setFileId(file.getId());
            AudioFormat format = ais.getFormat();
            long frames = ais.getFrameLength();
            song.setDuration((frames + 0.0) / format.getFrameRate());
        } catch (IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public FileStreamDto downloadFile(UUID id, BucketName bucketName) {
        FileDto dto = getById(id);
        return FileStreamDto.builder()
                .inputStream(() -> minioAdapterService.downloadFile(dto.getFilename(), bucketName.getTitle()))
                .filename(dto.getFilename())
                .build();
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
