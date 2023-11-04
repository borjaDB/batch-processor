DROP TABLE people IF EXISTS;

CREATE TABLE people  (
    id bigint auto_increment PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

CREATE TABLE profession  (
    id bigint auto_increment PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    profession VARCHAR(20)
);