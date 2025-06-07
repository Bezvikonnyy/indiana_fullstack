ALTER TABLE games ADD COLUMN author_id BIGINT;
ALTER TABLE games ADD CONSTRAINT fk_games_author FOREIGN KEY (author_id) REFERENCES users(id);