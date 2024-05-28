create schema if not exists song_stat;

create table if not exists song_stat.stat
(
    id                uuid not null
        primary key,
    title             text,
    album_id          uuid,
    duration          double precision,
    file_id           uuid,
    lyrics            text,
    created_timestamp timestamp with time zone default now(),
    updated_timestamp timestamp with time zone default now(),
    created_by        uuid,
    updated_by        uuid,
    sequence_number   integer                  default 0,
    history_amount    bigint,
    like_amount       bigint,
    playlist_amount   bigint
);

create schema if not exists user_prediction;

create table if not exists user_prediction.user_song_stat
(
    source  text not null,
    song_id uuid not null,
    user_id uuid not null,
    constraint user_song_stat_pk
        primary key (source, user_id, song_id)
);