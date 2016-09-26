CREATE TABLE sites (
    id UUID PRIMARY KEY not null,
    name VARCHAR(30) not null,
    url VARCHAR(100) not null,
    timezone VARCHAR(50) not null,
    account_id UUID REFERENCES accounts (id) not null
);
