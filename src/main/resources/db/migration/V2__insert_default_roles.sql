insert into core_service.authority (code, title)
values ('DEFAULT', 'DEFAULT'),
       ('ADMIN_AUTHORITY', 'ADMIN');

insert into core_service.role(code, title)
values ('DEFAULT_USER', 'user'),
       ('ADMIN', 'admin');

insert into core_service.role_authority (role_id, authority_code) SELECT id, 'DEFAULT' from core_service.role where code = 'DEFAULT_USER';
insert into core_service.role_authority (role_id, authority_code) SELECT id, 'ADMIN_AUTHORITY' from core_service.role where code = 'ADMIN'