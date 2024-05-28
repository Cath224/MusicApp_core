alter table core_service.like
    drop constraint like_user_id_fkey;
alter table core_service.like
    add constraint like_user_id_fkey foreign key (user_id) references core_service."user" (id);