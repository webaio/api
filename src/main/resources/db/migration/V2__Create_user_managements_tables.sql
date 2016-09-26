CREATE TABLE accounts (
    id UUID PRIMARY KEY not null,
    name VARCHAR(30) not null
);

CREATE TABLE roles (
    id UUID PRIMARY KEY not null,
    name VARCHAR (20) not null
);

CREATE TABLE users (
    id UUID PRIMARY KEY not null,
    username VARCHAR(50) not null,
    password VARCHAR(100) not null,
    first_name VARCHAR(30) not null,
    last_name VARCHAR(30) not null,
    account_id UUID REFERENCES accounts (id) not null,
    role_id UUID REFERENCES roles (id) not null,
    enabled BOOLEAN DEFAULT TRUE
);
