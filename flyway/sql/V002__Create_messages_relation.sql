CREATE TABLE messages (
    id        UUID         NOT NULL,
    client_id UUID         NOT NULL,

    content   TEXT         NOT NULL,
    status    VARCHAR(255) NOT NULL,

    CONSTRAINT messages_pkey  PRIMARY KEY (id),
    CONSTRAINT client_id_fkey FOREIGN KEY (client_id) REFERENCES clients
);
