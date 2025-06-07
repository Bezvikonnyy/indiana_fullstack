CREATE TABLE request_users (
    id SERIAL PRIMARY KEY,
    body_request VARCHAR(500) NOT NULL,
    user_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_user_request FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);