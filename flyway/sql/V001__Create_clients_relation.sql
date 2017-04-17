CREATE TABLE clients (
    id         UUID      NOT NULL,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT clients_pkey PRIMARY KEY (id)
);
