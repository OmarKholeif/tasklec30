CREATE TABLE IF NOT EXISTS students
(
    id          UUID PRIMARY KEY,
    full_name   VARCHAR(31)    NOT NULL,
    age         INT            NOT NULL,
    email       VARCHAR UNIQUE NOT NULL,
    password    VARCHAR        NOT NULL,
    is_login    BOOLEAN        NOT NULL,
    score       FLOAT,
    final_score FLOAT
);

ALTER TABLE students
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;
ALTER TABLE students
    ADD COLUMN IF NOT EXISTS is_archived BOOLEAN;

CREATE TABLE IF NOT EXISTS addresses
(
    id               UUID PRIMARY KEY,
    country          VARCHAR(30) NOT NULL,
    governance       VARCHAR(30) NOT NULL,
    city             VARCHAR(30) NOT NULL,
    street           VARCHAR     NOT NULL,
    building_number  INT         NOT NULL,
    floor_number     INT         NOT NULL,
    apartment_number INT         NOT NULL
);

ALTER TABLE students
    ADD COLUMN IF NOT EXISTS address_id UUID;

ALTER TABLE students
    ADD CONSTRAINT fk_address_addressId
        FOREIGN KEY (address_id) REFERENCES addresses (id);

INSERT INTO addresses
VALUES (gen_random_uuid(), 'Arab Republic of Egypt', 'Cairo', 'New Cairo', 'Gamal Abdelnasser', 100, 1, 1);
