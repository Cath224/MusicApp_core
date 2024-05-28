create table if not exists core_service.click_history
(
    id                uuid          not null   default gen_random_uuid() primary key,
    user_id           uuid          not null,
    element_type      varchar(1024) not null,
    element_id        varchar(36)   not null,
    search            boolean                  default false not null,
    created_timestamp timestamp with time zone default now(),
    updated_timestamp timestamp with time zone default now()
);

create index if not exists core_service_click_history_user_id on core_service.click_history (user_id);
create index if not exists core_service_click_history_element_id on core_service.click_history (element_id);
create index if not exists core_service_click_history_element_id_element_type on core_service.click_history (element_id, element_type);