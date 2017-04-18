#!/bin/bash

set -eu

DB_HOST=db
DB_USERNAME=postgres
DB_PASSWORD=
DB_PORT=5432

HOST_IP=$1
SERVICE_NAME=$2

echo "Running migrations on: $SERVICE_NAME"

docker run \
    --rm \
    --add-host db:$HOST_IP \
    -v $(pwd)/flyway/:/root/db davidvuong/flyway:local \
    flyway migrate \
        -user=$DB_USERNAME \
        -password=$DB_PASSWORD \
        -url=jdbc:postgresql://$DB_HOST:$DB_PORT/$SERVICE_NAME \
        -locations=filesystem:/root/db/sql-$SERVICE_NAME
