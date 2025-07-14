-- Увеличить длину поля details в games
ALTER TABLE games
ALTER COLUMN details TYPE VARCHAR(2000);

-- Переименовать колонку content -> text
ALTER TABLE comments
    RENAME COLUMN content TO text;

-- Изменить тип поля text, если нужно (например, если был TEXT, а ты хочешь VARCHAR(5000))
ALTER TABLE comments
ALTER COLUMN text TYPE VARCHAR(5000);

-- Добавить колонку author_id
ALTER TABLE comments
    ADD COLUMN author_id BIGINT;

-- Добавить внешний ключ на users
ALTER TABLE comments
    ADD CONSTRAINT fk_comment_author
        FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE;

-- Добавить колонку created_at
ALTER TABLE comments
    ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT now();
