create table if not exists song_stat.click_stat
(
    id                uuid          not null primary key,
    el_title          text,
    user_id           uuid          not null,
    element_type      varchar(1024) not null,
    element_id        varchar(36)   not null,
    search            boolean                  default false not null,
    created_timestamp timestamp with time zone default now(),
    updated_timestamp timestamp with time zone default now()
);