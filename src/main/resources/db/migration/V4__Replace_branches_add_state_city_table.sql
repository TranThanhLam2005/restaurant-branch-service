DROP TABLE IF EXISTS branches CASCADE;


CREATE TABLE states (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE cities (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    state_id BIGSERIAL NOT NULL,

    CONSTRAINT fk_cities_state
        FOREIGN KEY (state_id)
        REFERENCES states(id)
        ON DELETE CASCADE,

    UNIQUE (name, state_id)
);


CREATE TABLE branches (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    city_id BIGSERIAL NOT NULL,
    opening_time TIME,
    closing_time TIME,

    CONSTRAINT fk_branches_city
        FOREIGN KEY (city_id)
        REFERENCES cities(id)
        ON DELETE CASCADE
);