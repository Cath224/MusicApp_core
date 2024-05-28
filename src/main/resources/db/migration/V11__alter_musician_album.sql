alter table core_service.album
    drop constraint album_musician_id_fkey;
alter table core_service.album
    add constraint album_musician_id_fkey foreign key (musician_id) references core_service.musician (id) on DELETE cascade;

alter table core_service.song
    drop constraint song_album_id_fkey;
alter table core_service.song
    add constraint song_album_id_fkey foreign key (album_id) references core_service.album (id) on DELETE cascade;

alter table core_service.like
    drop constraint like_song_id_fkey;
alter table core_service."like"
    add constraint like_song_id_fkey foreign key (song_id) references core_service.song (id) on DELETE cascade;

alter table core_service.history
    drop constraint history_song_id_fkey;
alter table core_service.history
    add constraint history_song_id_fkey foreign key (song_id) references core_service.song (id) on DELETE cascade;