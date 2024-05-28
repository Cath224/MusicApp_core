package ru.musicapp.coreservice.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BucketName {

    PICTURE("pictures"),
    SONG("songs");

    private final String title;

}
