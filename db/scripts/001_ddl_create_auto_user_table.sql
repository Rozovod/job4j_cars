create table if not exists auto_user
(
    id serial primary key,
    name varchar not null,
    login varchar not null unique,
    password varchar not null
);