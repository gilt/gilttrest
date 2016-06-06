# Users schema

# --- !Ups

CREATE TABLE users (
    id bigserial primary key,
    name varchar(255) NOT NULL,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);

create unique index on users(username);

insert into users values (default, 'otto', 'ottoman', 'password123');

# --- !Downs

DROP TABLE users;

