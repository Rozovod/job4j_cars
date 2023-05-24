create table if not exists auto_post
(
    id serial primary key,
    description varchar not null,
    created timestamp not null,
    car_id int not null unique REFERENCES car(id),
    productionYear int,
    mileage int,
    carNew boolean,
    carSold boolean,
    price int,
    auto_user_id int references auto_user (id) not null
);