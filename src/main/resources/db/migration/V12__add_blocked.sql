create extension if not exists pg_trgm;

alter table core_service."user"
    add column if not exists file_id uuid references core_service.file (id);

alter table core_service."user"
    add column if not exists blocked boolean not null default false;

create index if not exists core_service_song_album_id on core_service.song (album_id);
create index if not exists core_service_album_musician_id on core_service.album (musician_id);
create index if not exists core_service_like_user_id on core_service."like" (user_id);
create index if not exists core_service_history_user_id on core_service.history (user_id);
create index if not exists core_service_playlist_user_id on core_service.playlist (created_by);

create index if not exists core_service_song_title on core_service.song using gist (title gist_trgm_ops);
create index if not exists core_service_album_title on core_service.album using gist (title gist_trgm_ops);
create index if not exists core_service_musician_title on core_service.musician using gist (title gist_trgm_ops);
create index if not exists core_service_playlist_title on core_service.playlist using gist (title gist_trgm_ops);
