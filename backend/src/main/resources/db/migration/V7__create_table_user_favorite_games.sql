CREATE TABLE user_favorite_games(
    user_id BIGINT NOT NULL,
    game_id BIGINT NOT NULL,
    PRIMARY KEY(user_id, game_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE
);
