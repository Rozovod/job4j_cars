create table if not exists car (
    id serial primary key,
    name varchar not null,
    model varchar not null,
    category_id int not null references category(id),
    body_id int not null references body(id),
    engine_id int not null references engine(id),
    transmission_id int not null references transmission(id),
    owner_id int not null references owner(id)
);