create schema if not exists core_service;


create table if not exists core_service.salt
(
    id                uuid not null            default gen_random_uuid() primary key,
    value             varchar(32),
    created_timestamp timestamp with time zone default now(),
    updated_timestamp timestamp with time zone default now()
);


create table if not exists core_service.credentials
(
    id                uuid                 not null default gen_random_uuid() primary key,
    login             varchar(1024) unique not null,
    password          varchar(256)         not null,
    salt_id           uuid                 not null unique references core_service.salt (id),
    created_timestamp timestamp with time zone      default now(),
    updated_timestamp timestamp with time zone      default now()
);

create table if not exists core_service.user
(
    id                uuid          not null   default gen_random_uuid() primary key,
    name              varchar(1024) not null,
    credentials_id    uuid          not null unique references core_service.credentials (id),
    email             varchar(256)  not null unique,
    links             varchar(2024)[],
    created_timestamp timestamp with time zone default now(),
    updated_timestamp timestamp with time zone default now()
);


create table if not exists core_service.role
(
    id                uuid        not null     default gen_random_uuid() primary key,
    code              text unique not null,
    title             text unique not null,
    created_timestamp timestamp with time zone default now(),
    updated_timestamp timestamp with time zone default now()
);

create table if not exists core_service.authority
(
    code  text        not null primary key,
    title text unique not null
);

create table if not exists core_service.role_authority
(
    role_id        uuid not null references core_service.role (id),
    authority_code text not null references core_service.authority (code),
    primary key (role_id, authority_code)
);

create table if not exists core_service.user_role
(
    user_id uuid not null references core_service.user (id),
    role_id uuid not null references core_service.role (id),
    primary key (user_id, role_id)
);


create table if not exists core_service.file
(
    id uuid not null default gen_random_uuid() primary key,
    filename text not null,
    mime_type text not null,
    created_timestamp timestamp with time zone default now(),
    updated_timestamp timestamp with time zone default now(),
    created_by        uuid references core_service.user (id),
    updated_by        uuid references core_service.user (id)
);

create table if not exists core_service.tag
(
    title             varchar(2048) not null primary key,
    created_timestamp timestamp with time zone default now(),
    updated_timestamp timestamp with time zone default now(),
    created_by        uuid references core_service.user (id),
    updated_by        uuid references core_service.user (id)
);

create table if not exists core_service.musician
(
    id                uuid not null            default gen_random_uuid() primary key,
    title             text not null,
    file_id           uuid references core_service.file (id),
    description       text,
    short_description varchar(1024),
    links             text[],
    country_code      varchar(3),
    created_timestamp timestamp with time zone default now(),
    updated_timestamp timestamp with time zone default now(),
    created_by        uuid references core_service.user (id),
    updated_by        uuid references core_service.user (id)
);

create table if not exists core_service.musician_tag
(
    tag_title         varchar(2048) not null references core_service.tag (title),
    musician_id       uuid          not null references core_service.musician (id),
    created_timestamp timestamp with time zone default now(),
    created_by        uuid references core_service.user (id),
    primary key (tag_title, musician_id)
);

create table if not exists core_service.album
(
    id                uuid not null            default gen_random_uuid() primary key,
    title             text not null,
    musician_id       uuid not null references core_service.musician (id),
    ep                boolean                  default false,
    year              int2,
    file_id           uuid references core_service.file (id),
    short_description varchar(1024),
    created_timestamp timestamp with time zone default now(),
    updated_timestamp timestamp with time zone default now(),
    created_by        uuid references core_service.user (id),
    updated_by        uuid references core_service.user (id)
);

create table if not exists core_service.album_tag
(
    tag_title         varchar(2048) not null references core_service.tag (title),
    album_id          uuid          not null references core_service.album (id),
    created_timestamp timestamp with time zone default now(),
    created_by        uuid references core_service.user (id),
    primary key (tag_title, album_id)
);

create table if not exists core_service.song
(
    id                uuid not null            default gen_random_uuid() primary key,
    title             text not null,
    album_id          uuid not null references core_service.album (id),
    duration          integer,
    file_id           uuid references core_service.file (id),
    lyrics            text,
    created_timestamp timestamp with time zone default now(),
    updated_timestamp timestamp with time zone default now(),
    created_by        uuid references core_service.user (id),
    updated_by        uuid references core_service.user (id)
);

create table if not exists core_service.playlist
(
    id                uuid not null            default gen_random_uuid() primary key,
    title             text not null,
    file_id           uuid references core_service.file (id),
    private           boolean                  default true,
    created_timestamp timestamp with time zone default now(),
    updated_timestamp timestamp with time zone default now(),
    created_by        uuid references core_service.user (id),
    updated_by        uuid references core_service.user (id)
);

create table if not exists core_service.playlist_song
(
    playlist_id uuid not null references core_service.playlist (id),
    song_id     uuid not null references core_service.song (id),
    primary key (playlist_id, song_id)
);

create table if not exists core_service.like
(
    user_id uuid not null references core_service.playlist (id),
    song_id uuid not null references core_service.song (id),
    primary key (user_id, song_id)
);

create table if not exists core_service.history
(
    user_id uuid not null references core_service.playlist (id),
    song_id uuid not null references core_service.song (id),
    primary key (user_id, song_id)
);

