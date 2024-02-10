-- Database: devsu

-- DROP DATABASE IF EXISTS devsu;

CREATE DATABASE devsu
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Colombia.1252'
    LC_CTYPE = 'Spanish_Colombia.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE client (
    id UUID PRIMARY KEY default gen_random_uuid(),
    password VARCHAR(255),
    status bool,
    identification BIGINT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    gender VARCHAR(255),
    age INTEGER,
    address VARCHAR(255),
    phone_number VARCHAR(255),
    creation_date TIMESTAMP WITH TIME ZONE,
    creation_user VARCHAR(255),
    update_date TIMESTAMP WITH TIME ZONE,
    update_user VARCHAR(255)
);

CREATE SEQUENCE account_seq START 100000;

CREATE TABLE account (
    id BIGINT PRIMARY KEY DEFAULT nextval('account_seq'),
    type VARCHAR(255),
    initial_balance DOUBLE PRECISION,
    status BOOLEAN,
    current_balance DOUBLE PRECISION,
    client_id BIGINT,
    creation_date TIMESTAMP WITH TIME ZONE,
    creation_user VARCHAR(255),
    update_date TIMESTAMP WITH TIME ZONE,
    update_user VARCHAR(255)
);


CREATE TABLE account_movement (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id BIGINT,
    amount DOUBLE PRECISION,
    movement_date TIMESTAMP WITH TIME ZONE,
    balance DOUBLE PRECISION,
    creation_date TIMESTAMP WITH TIME ZONE,
    creation_user VARCHAR(255),
    update_date TIMESTAMP WITH TIME ZONE,
    update_user VARCHAR(255)
);