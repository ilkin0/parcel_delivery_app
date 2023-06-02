#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE parcel_delivery_db;
    GRANT ALL PRIVILEGES ON DATABASE parcel_delivery_db TO $POSTGRES_USER;
EOSQL
