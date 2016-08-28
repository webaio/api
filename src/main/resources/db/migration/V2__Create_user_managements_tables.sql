CREATE TABLE accounts (
    id UUID PRIMARY KEY not null,
    name VARCHAR(100) not null
);

CREATE TABLE users (
    id UUID PRIMARY KEY not null,
    email VARCHAR(100) not null,
    password VARCHAR(100) not null,
    first_name VARCHAR(50) not null,
    last_name VARCHAR(50) not null,
    account_id UUID REFERENCES accounts (id) not null
);

CREATE TABLE roles (
    id UUID PRIMARY KEY not null,
    name VARCHAR (20) not null
);

CREATE TABLE users_roles (
    user_id UUID REFERENCES users (id) not null,
    role_id UUID REFERENCES roles (id) not null,
    PRIMARY KEY (user_id, role_id)
);
