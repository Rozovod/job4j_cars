create table if not exists owner (
    id serial primary key,
    name varchar not null,
    phone varchar not null,
    user_id int not null unique references auto_user(id)
);