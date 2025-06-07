CREATE TABLE games(
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    details VARCHAR(500) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    game_file_url VARCHAR(255) NOT NULL
);

CREATE TABLE categories(
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL UNIQUE
);

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

CREATE TABLE game_category(
    game_id BIGINT NOT NULL ,
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
