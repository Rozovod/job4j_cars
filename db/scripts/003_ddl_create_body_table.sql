create table if not exists body
(
    id serial primary key,
    name varchar not null,
    category_id int not null REFERENCES category(id)
);