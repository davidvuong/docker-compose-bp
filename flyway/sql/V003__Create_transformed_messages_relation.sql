CREATE TABLE transformed_messages (
    id         UUID NOT NULL,
    message_id UUID NOT NULL,

    content    TEXT NOT NULL,

    CONSTRAINT transformed_messages_pkey PRIMARY KEY (id),
    CONSTRAINT message_id_fkey           FOREIGN KEY (message_id) REFERENCES messages
);
