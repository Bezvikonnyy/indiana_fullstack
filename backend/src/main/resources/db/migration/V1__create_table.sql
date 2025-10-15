CREATE TABLE roles
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL
);

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role_id    BIGINT       NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT now(),
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE request_users
(
    id           SERIAL PRIMARY KEY,
    body_request VARCHAR(500) NOT NULL,
    user_id      BIGINT       NOT NULL UNIQUE,
    CONSTRAINT fk_user_request FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE games
(
    id            SERIAL PRIMARY KEY,
    title         VARCHAR(50)    NOT NULL,
    details       VARCHAR(2000)  NOT NULL,
    image_url     VARCHAR(255)   NOT NULL,
    game_file_url VARCHAR(255)   NOT NULL,
    author_id     BIGINT,
    price         numeric(10, 2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_games_author FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE TABLE categories
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE game_category
(
    game_id     BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (game_id, category_id),
    FOREIGN KEY (game_id) REFERENCES games (id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

CREATE TABLE comments
(
    id         SERIAL PRIMARY KEY,
    text       VARCHAR(5000) NOT NULL,
    game_id    BIGINT        NOT NULL,
    author_id  BIGINT REFERENCES users (id) ON DELETE CASCADE,
    created_at TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT fk_comment_game FOREIGN KEY (game_id) REFERENCES games (id) ON DELETE CASCADE
);

CREATE TABLE invite_code
(
    id         SERIAL PRIMARY KEY,
    code       VARCHAR(500) NOT NULL UNIQUE,
    used       BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP    NOT NULL,
    expires_at TIMESTAMP
);

CREATE TABLE user_orders
(
    id           SERIAL PRIMARY KEY,
    user_id      BIGINT         NOT NULL REFERENCES users (id),
    status       VARCHAR(20)    NOT NULL,
    created_at   TIMESTAMP      NOT NULL,
    total_amount numeric(10, 2) NOT NULL
);

CREATE INDEX idx_user_orders_user_id ON user_orders (user_id);

CREATE TABLE cart
(
    id      BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE order_item
(
    id       SERIAL PRIMARY KEY,
    order_id BIGINT         NOT NULL REFERENCES user_orders (id),
    game_id  BIGINT         NOT NULL REFERENCES games (id),
    price    numeric(10, 2) NOT NULL,
    quantity INTEGER        NOT NULL DEFAULT 1
);

ALTER TABLE order_item
    ADD CONSTRAINT uq_order_item UNIQUE (order_id, game_id);

CREATE TABLE payments
(
    id             SERIAL PRIMARY KEY,
    order_id       BIGINT         NOT NULL REFERENCES user_orders (id),
    amount         numeric(10, 2) NOT NULL,
    payment_method VARCHAR(20)    NOT NULL,
    transaction_id VARCHAR(100),
    status         VARCHAR(20)    NOT NULL,
    created_at     TIMESTAMP      NOT NULL,
    updated_at     TIMESTAMP      NOT NULL
);

CREATE INDEX idx_payments_order_id ON payments (order_id);

CREATE UNIQUE INDEX idx_payments_transaction_id ON payments (transaction_id);

CREATE TABLE cart_item
(
    id      SERIAL PRIMARY KEY,
    game_id BIGINT NOT NULL REFERENCES games (id),
    cart_id BIGINT NOT NULL REFERENCES cart (id)
);

ALTER TABLE cart_item
    ADD CONSTRAINT uq_cart_item UNIQUE (cart_id, game_id);

ALTER TABLE cart
    ADD CONSTRAINT unique_user_cart UNIQUE (user_id);

CREATE TABLE user_purchased_games
(
    user_id BIGINT NOT NULL,
    game_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, game_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES games (id) ON DELETE CASCADE
);

CREATE TABLE user_favorite_games
(
    user_id BIGINT NOT NULL,
    game_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, game_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES games (id) ON DELETE CASCADE
);

INSERT INTO categories (title)
VALUES ('Шутеры'),
       ('Платформеры'),
       ('Хак-н-слэш'),
       ('Bea tem up'),
       ('Линейные приключения'),
       ('Песочница'),
       ('Квесты'),
       ('Рогалики'),
       ('Экшн-RPG'),
       ('Тактические RPG'),
       ('ММО RPG'),
       ('Инди RPG'),
       ('В реальном времени'),
       ('Пошаговые стратегии'),
       ('Гибридные стратегии'),
       ('Симуляторы гонок'),
       ('Аркадные гонки'),
       ('Симуляторы жизни'),
       ('Симуляторы полетов'),
       ('Симуляторы строительства'),
       ('Экономические симуляторы'),
       ('Психологический хоррор'),
       ('Хоррор с выживанием'),
       ('Зомби-апокалипсис'),
       ('Логические головоломки'),
       ('Математические игры'),
       ('Футбол'),
       ('Баскетбол'),
       ('Гольф'),
       ('Рэгби'),
       ('Музыкальные игры'),
       ('Ритм-игры');

INSERT INTO roles (title)
VALUES ('ROLE_USER'),
       ('ROLE_AUTHOR'),
       ('ROLE_ADMIN');
