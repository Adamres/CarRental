CREATE TABLE cars
(
    id serial,
    name text,
    price integer,
    photoURL text,
    active text DEFAULT 'active',
    PRIMARY KEY (id)
);

CREATE TABLE reservations
(
    id serial,
    car_id integer REFERENCES cars (id),
    start_date date,
    end_date date,
    name text,
    address text,
    email text,
    phone text,
    total_price integer,
    PRIMARY KEY (id)
);


