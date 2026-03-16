CREATE TABLE tags (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL CONSTRAINT uq_tags_name UNIQUE,
    color       VARCHAR(7),
    created_by  BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE task_tags (
    task_id BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    tag_id  BIGINT NOT NULL REFERENCES tags(id)  ON DELETE CASCADE,
    PRIMARY KEY (task_id, tag_id)
);