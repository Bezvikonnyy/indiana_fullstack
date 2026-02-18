ALTER TABLE games
    ADD COLUMN purchases_count BIGINT NOT NULL DEFAULT 0;

CREATE TABLE game_ratings
(
    id         SERIAL PRIMARY KEY,
    user_id    BIGINT    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    game_id    BIGINT    NOT NULL REFERENCES games (id) ON DELETE CASCADE,
    rating     INT       NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    UNIQUE (user_id, game_id)
);

CREATE TABLE game_discounts
(
    id               SERIAL PRIMARY KEY,
    game_id          BIGINT    NOT NULL REFERENCES games (id) ON DELETE CASCADE,
    discount_percent INT       NOT NULL,
    start_date       TIMESTAMP NOT NULL,
    end_date         TIMESTAMP NOT NULL,
    active           BOOLEAN   NOT NULL DEFAULT TRUE
);

ALTER TABLE user_purchased_games
    ADD COLUMN price_at_moment NUMERIC(10, 2) NOT NULL DEFAULT 0;

ALTER TABLE games
    ADD COLUMN ratings_count BIGINT NOT NULL DEFAULT 0,
ADD COLUMN average_rating DOUBLE PRECISION NOT NULL DEFAULT 0.0;
