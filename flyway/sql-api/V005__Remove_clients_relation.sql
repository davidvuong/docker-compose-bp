/* Remove the foreign key constraint */
ALTER TABLE "messages" DROP "client_id";

/* Remove the clients relation entirely */
DROP TABLE clients;
