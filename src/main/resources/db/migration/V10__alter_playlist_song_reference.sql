alter table core_service.playlist_song
    drop constraint playlist_song_playlist_id_fkey;
alter table core_service.playlist_song
    add constraint playlist_song_playlist_id_fkey foreign key (playlist_id) references core_service.playlist (id) on DELETE cascade;

alter table core_service.playlist_song
    drop constraint playlist_song_song_id_fkey;
alter table core_service.playlist_song
    add constraint playlist_song_song_id_fkey foreign key (song_id) references core_service.song (id) on DELETE cascade;