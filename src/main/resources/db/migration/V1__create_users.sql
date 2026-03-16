CREATE TABLE users (
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(50),
    email       VARCHAR(255) NOT NULL CONSTRAINT uq_users_email UNIQUE,
    password    VARCHAR(255) NOT NULL,
    first_name  VARCHAR(100),
    last_name   VARCHAR(100),
    role        VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);