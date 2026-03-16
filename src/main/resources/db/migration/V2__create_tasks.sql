CREATE TABLE tasks (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    status      VARCHAR(20) NOT NULL DEFAULT 'TODO',
    priority    VARCHAR(20) NOT NULL DEFAULT 'MEDIUM',
    due_date    DATE,
    owner_id    BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    assignee_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);