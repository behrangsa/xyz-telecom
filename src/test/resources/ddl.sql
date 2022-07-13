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
    phone_number varchar(32) NOT NULL UNIQUE,
    is_active    BOOL        NOT NULL
);;

CREATE INDEX ON phone_numbers (customer_id);
CREATE INDEX ON phone_numbers (phone_number, is_active);
