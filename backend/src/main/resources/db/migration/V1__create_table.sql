CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE request_users (
    id SERIAL PRIMARY KEY,
    body_request VARCHAR(500) NOT NULL,
    user_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_user_request FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE games (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    details VARCHAR(500) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    game_file_url VARCHAR(255) NOT NULL,
    author_id BIGINT,
    CONSTRAINT fk_games_author FOREIGN KEY (author_id) REFERENCES users(id)
);

CREATE TABLE categories(
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE game_category(
    game_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (game_id, category_id),
    FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

CREATE TABLE comments(
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    game_id BIGINT NOT NULL,
    FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE
);

-- Данные
INSERT INTO categories (title)
VALUES
    ('Шутеры'),
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
VALUES
    ('ROLE_USER'),
    ('ROLE_AUTHOR'),
    ('ROLE_ADMIN');
