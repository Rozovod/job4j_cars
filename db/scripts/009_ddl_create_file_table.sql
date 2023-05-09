create table if not exists file (
    id serial primary key,
    name varchar not null,
    path varchar not null unique,
    auto_post_id int REFERENCES auto_post (id)
);