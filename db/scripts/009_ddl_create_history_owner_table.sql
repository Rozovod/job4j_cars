create table history_owner (
    id serial primary key,
    owner_id int not null references owner(id),
    car_id int not null references car(id),
    startAt timestamp not null,
    endAt timestamp not null
);