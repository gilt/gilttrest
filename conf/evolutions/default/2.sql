# Pins schema

# --- !Ups

CREATE TABLE pins (
    id bigserial primary key,
    user_id bigint not null references users(id) on delete cascade,
    sale_key text not null
);


# --- !Downs

DROP TABLE pins;
