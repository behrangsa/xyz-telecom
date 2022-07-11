CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS customer CASCADE;
CREATE TABLE customer
(
    id   UUID         NOT NULL PRIMARY KEY,
    name varchar(128) NOT NULL
);;

DROP TABLE IF EXISTS phone_numbers CASCADE;
CREATE TABLE phone_numbers
(
    id           UUID        NOT NULL PRIMARY KEY,
    customer_id  UUID        NOT NULL REFERENCES customer (id),
    phone_number varchar(32) NOT NULL,
    is_active    BOOL        NOT NULL
);;