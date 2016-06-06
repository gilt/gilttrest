# Pins schema

# --- !Ups

CREATE TABLE pins (
    id bigserial primary key,
    user_id bigint not null references users(id),
    sale_key text not null,
    expires_at timestamptz not null
);

create index on pins(expires_at);


# --- !Downs

DROP TABLE pins;
