create table if not exists core_service.genre
(
    title             varchar(2048) not null primary key,
    created_timestamp timestamp with time zone default now(),
    updated_timestamp timestamp with time zone default now()
);

create table if not exists core_service.album_genre
(
    album_id uuid not null references core_service.album (id) on DELETE cascade,
    genre text not null references core_service.genre (title) on delete cascade,
    primary key (album_id, genre)
);