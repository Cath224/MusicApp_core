alter table core_service.credentials drop column if exists salt_id;

drop table if exists core_service.salt;