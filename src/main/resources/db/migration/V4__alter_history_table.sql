alter table core_service.history
    drop constraint history_user_id_fkey;
alter table core_service.history
    add constraint history_user_id_fkey foreign key (user_id) references core_service."user" (id);